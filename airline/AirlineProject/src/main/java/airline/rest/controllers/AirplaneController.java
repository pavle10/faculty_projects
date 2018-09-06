package airline.rest.controllers;

import airline.dao.AirplaneRepository;
import airline.dao.BoardingTicketRepository;
import airline.dao.FlightRepository;
import airline.dao.TicketRepository;
import airline.entites.Airplane;
import airline.entites.BoardingTicket;
import airline.entites.Flight;
import airline.entites.Ticket;
import airline.util.AirplaneStats;
import airline.util.JSONUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/airplane")
@CrossOrigin(origins = "*")
public class AirplaneController {

    @Autowired
    protected AirplaneRepository airplaneRepository;
    @Autowired
    protected FlightRepository flightRepository;
    @Autowired
    protected TicketRepository ticketRepository;
    @Autowired
    protected BoardingTicketRepository boardingTicketRepository;

    @GetMapping("/getAll")
    public List<Airplane> getAll() {
        return airplaneRepository.findAll();
    }

    @PostMapping("/add")
    public String addAirplane(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String reg = jsonObject.getString("regnum");
            String brand = jsonObject.getString("brand");
            String model = jsonObject.getString("model");
            int seatNum = Integer.valueOf(jsonObject.getString("seatnum"));
            int cols = Integer.valueOf(jsonObject.getString("cols"));
            int rows = Integer.valueOf(jsonObject.getString("rows"));
            int colsnum = Integer.valueOf(jsonObject.getString("colsnum"));
            int distance = Integer.valueOf(jsonObject.getString("distance"));
            int speed = Integer.valueOf(jsonObject.getString("speed"));
            Airplane ap = airplaneRepository.findByRegistration(reg);
            if (ap != null) return "{\"status\":\"REG\"}";
            Airplane airplane = new Airplane();
            airplane.setRegistration(reg);
            airplane.setBrand(brand);
            airplane.setModel(model);
            airplane.setSeats_num(seatNum);
            airplane.setCols_num(cols);
            airplane.setRows_num(rows);
            airplane.setCol_seat_num(colsnum);
            airplane.setFly_distance(distance);
            airplane.setSpeed(speed);
            airplaneRepository.save(airplane);
        } catch (IOException e) {
            return "{\"status\":\"FAIL\"}";
        }
        return "{\"status\":\"OK\"}";
    }

    @PostMapping("/remove")
    public String removeAirplane(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String reg = jsonObject.getString("regnum");
            Airplane airplane = airplaneRepository.findByRegistration(reg);
            if (airplane == null) return "{\"status\":\"FAIL\"}";
            airplaneRepository.delete(airplane);
        } catch (IOException e) {
            return "{\"status\":\"FAIL\"}";
        }
        return "{\"status\":\"OK\"}";
    }

    @PostMapping("/stats")
    public List<AirplaneStats> getStats(HttpServletRequest req) {
        List<AirplaneStats> result = new ArrayList<>();
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String reg = jsonObject.getString("anumber");
            Airplane airplane = airplaneRepository.findByRegistration(reg);
            if (airplane == null) return null;
            List<Flight> flights = flightRepository.getByAirplaneid(airplane.getId());
            for (Flight f: flights) {
                AirplaneStats airplaneStats1 = new AirplaneStats();
                airplaneStats1.setRegistration(reg);
                airplaneStats1.setBrand(airplane.getBrand());
                airplaneStats1.setModel(airplane.getModel());
                airplaneStats1.setNumOfSeats(airplane.getSeats_num());
                airplaneStats1.setRows(airplane.getRows_num());
                airplaneStats1.setCols(airplane.getCols_num());
                airplaneStats1.setNumCols(airplane.getCol_seat_num());
                airplaneStats1.setFlyDistance(airplane.getFly_distance());
                airplaneStats1.setSpeed(airplane.getSpeed());
                airplaneStats1.setFlightNumber(f.getNumber());
                List<BoardingTicket> bts = boardingTicketRepository.findByFnumber(f.getNumber());
                int numOfPass = bts.size();
                List<Ticket> tickets = ticketRepository
                        .findByFlightnumberAndAndNumberGreaterThan(f.getNumber(), 0);
                for (Ticket t: tickets) {
                    numOfPass += t.getNumber();
                }
                airplaneStats1.setNumOfPass(numOfPass);
                result.add(airplaneStats1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
