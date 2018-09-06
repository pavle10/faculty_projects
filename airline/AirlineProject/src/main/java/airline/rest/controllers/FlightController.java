package airline.rest.controllers;

import airline.dao.*;
import airline.entites.*;
import airline.util.FlightStat;
import airline.util.JSONUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/flight")
@CrossOrigin(origins = "*")
public class FlightController {

    public static final long HOUR = 3600 * 1000;
    public static final long MINUTE = 60 * 1000;

    @Autowired
    protected FlightRepository flightRepository;
    @Autowired
    protected DestinationRepository destinationRepository;
    @Autowired
    protected AirplaneRepository airplaneRepository;
    @Autowired
    protected PriceRepository priceRepository;
    @Autowired
    protected TicketRepository ticketRepository;
    @Autowired
    protected BoardingTicketRepository boardingTicketRepository;


    @GetMapping("/getAll")
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @GetMapping("/getPrices")
    public List<Price> getPrices() {
        return priceRepository.findAll();
    }

    @PostMapping("/add")
    public String addFlight(HttpServletRequest req) throws ParseException {
        try {
            char[] letters = {'A', 'B', 'C', 'D', 'E'};
            Random rand = new Random();
            int gateNumber = rand.nextInt(5)+1;
            char gateLetter = letters[rand.nextInt(5)];
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String number = jsonObject.getString("fnumber");
            String startCode = jsonObject.getString("ddest");
            String destCode = jsonObject.getString("adest");
            String strTime = jsonObject.getString("datetime");
            int distance = Integer.valueOf(jsonObject.getString("distance"));
            int airId = Integer.valueOf(jsonObject.getString("airplaneid"));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = format.parse(strTime);

            Airplane airplane = airplaneRepository.findOne(airId);
            int speed = airplane.getSpeed();
            Destination startDest = destinationRepository.findByCode(startCode);
            Destination endDest = destinationRepository.findByCode(destCode);
            int startZone = startDest.getTime_zone();
            int endZone = endDest.getTime_zone();
            double flightTime = (double) distance/speed;
            int flightHours = (int)flightTime;
            int flightMinutes = (int)((flightTime - flightHours) * 60);
            long time = date.getTime();
            System.out.println("Start time(local): " + date);
            double temp = flightHours+flightMinutes*0.01;
            System.out.println("Flight time: " + temp);
            time -= startZone * HOUR;
            time += flightHours * HOUR + flightMinutes * MINUTE;
            time += endZone * HOUR;
            Date arrivelDate = new Date(time);
            System.out.println("End time(local): " + arrivelDate);

            if (!checkAirplaneValidity(airplane, date, arrivelDate)) {
                return "{\"status\":\"TIME\"}";
            }
            if (!checkDistance(airplane, distance)) {
                return "{\"status\":\"DISTANCE\"}";
            }

            Flight flight = new Flight();
            flight.setNumber(number);
            flight.setFrom_destination(startCode);
            flight.setTo_destination(destCode);
            flight.setDeparturetime(date);
            flight.setArrivaltime(arrivelDate);
            flight.setFlight_time(flightHours+flightMinutes*0.01);
            flight.setDistance(distance);
            flight.setAirplane_id(airId);
            flight.setGate(gateLetter + "" + gateNumber);
            flightRepository.save(flight);

            Price price = new Price();
            price.setFnumber(number);
            price.setPrice(rand.nextInt(1000) + 200);
            priceRepository.save(price);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\":\"OK\"}";
    }

    private boolean checkAirplaneValidity(Airplane airplane, Date start, Date end) {
        List<Flight> flights = flightRepository.getByAirplaneid(airplane.getId());
        if (flights.size() == 0) return true;

        for (Flight f: flights) {
            if (f.getDeparturetime().before(start) && f.getArrivaltime().after(start)) return false;
            if (f.getDeparturetime().before(end) && f.getArrivaltime().after(end)) return false;
            int diff = (int) ((f.getArrivaltime().getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
            if (diff < 2 && diff > -2) return false;
            diff = (int) ((end.getTime() - f.getDeparturetime().getTime()) / (1000 * 60 * 60 * 24));
            if (diff < 2 && diff > -2) return false;
        }

        return true;
    }

    private boolean checkDistance(Airplane airplane, int distance) {
        return airplane.getFly_distance() - distance >= 0;
    }

    @PostMapping("/remove")
    public String removeFlight(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String number = jsonObject.getString("fnumber");
            Flight flight = flightRepository.getByNumber(number);
            Price price = priceRepository.findByFnumber(number);
            if (price == null) return "{\"status\":\"FAIL\"}";
            if (flight == null) return "{\"status\":\"FAIL\"}";
            priceRepository.delete(price);
            flightRepository.delete(flight);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\":\"OK\"}";
    }

    @PostMapping("/changePrice")
    public String changePrice(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String number = jsonObject.getString("fnumber");
            int priceValue = Integer.valueOf(jsonObject.getString("newprice"));
            Price price = priceRepository.findByFnumber(number);
            if (price == null) return "{\"status\":\"FAIL\"}";
            price.setPrice(priceValue);
            priceRepository.save(price);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\":\"OK\"}";
    }

    @GetMapping("/currentFlights")
    public List<Flight> currentFlights() {
        return flightRepository.findByDeparturetimeAfter(new Date());
    }

    @PostMapping("/seats")
    public String seats(HttpServletRequest req) {
        StringBuilder msg = new StringBuilder("{\"status\":\"OK\",");
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            int ticketId = Integer.valueOf(jsonObject.getString("ticketid"));
            Ticket ticket = ticketRepository.findOne(ticketId);
            if (ticket == null) return "{\"status\":\"FAIL\"}";
            String fnumber = ticket.getFlightnumber();
            Flight flight = flightRepository.getByNumber(fnumber);
            if (flight == null) return "{\"status\":\"FAIL FLIGHT\"}";
            Airplane airplane = airplaneRepository.findOne(flight.getAirplane_id());
            if (airplane == null) return "{\"status\":\"FAIL AIRPLANE\"}";
            int rows = airplane.getRows_num();
            int colSeats = airplane.getCol_seat_num();
            int cols = airplane.getCols_num() * colSeats;
            msg.append("\"rows\":\"" + rows + "\", ");
            msg.append("\"cols\":\"" + cols + "\", ");
            msg.append("\"colsnum\":\"" + colSeats + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg.toString();
    }

    @GetMapping("/currentPrices")
    public List<Price> currentPrices() {
        List<Flight> currFlights = flightRepository.findByDeparturetimeAfter(new Date());
        List<Price> prices = priceRepository.findAll();
        List<Price> result = new ArrayList<>();

        for (Flight f: currFlights) {
            for (Price p: prices) {
                if (p.getFnumber().equals(f.getNumber())) {
                    result.add(p);
                    break;
                }
            }
        }
        return result;
    }

    @PostMapping("/stats")
    public List<FlightStat> getStats(HttpServletRequest req) {
        List<FlightStat> result = new ArrayList<>();
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String fnumber = jsonObject.getString("fnumber");
            Flight flight = flightRepository.getByNumber(fnumber);
            if (flight == null) return null;
            FlightStat flightStat = new FlightStat();
            flightStat.setFnumber(fnumber);
            flightStat.setAirplaneId(flight.getAirplane_id());
            flightStat.setFromDest(flight.getFrom_destination());
            flightStat.setToDest(flight.getTo_destination());
            flightStat.setDepDate(flightStat.getDepDate());
            flightStat.setArrDate(flightStat.getArrDate());
            flightStat.setDistance(flight.getDistance());
            flightStat.setfTime(flight.getFlight_time());
            flightStat.setGate(flight.getGate());
            List<Ticket> tickets = ticketRepository.findByFlightnumberAndAndNumberGreaterThan(fnumber, 0);
            List<BoardingTicket> bts = boardingTicketRepository.findByFnumber(fnumber);
            int number = bts.size();
            for (Ticket t: tickets) {
                number += t.getNumber();
            }
            flightStat.setNumOfPass(number);
            result.add(flightStat);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
