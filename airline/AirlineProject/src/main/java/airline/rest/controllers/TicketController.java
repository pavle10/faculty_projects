package airline.rest.controllers;

import airline.dao.*;
import airline.entites.*;
import airline.util.History;
import airline.util.JSONUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    protected TicketRepository ticketRepository;
    @Autowired
    protected PriceRepository priceRepository;
    @Autowired
    protected JavaMailSender javaMailSender;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected FlightRepository flightRepository;
    @Autowired
    protected DestinationRepository destinationRepository;
    @Autowired
    protected BoardingTicketRepository boardingTicketRepository;

    @GetMapping("/getTickets/{id}")
    public List<Ticket> getTickets(@PathVariable int id) {
        List<Ticket> tickets = ticketRepository.findByUserid(id);
        List<Ticket> result = new ArrayList<>();
        Date currDate = new Date();
        for (Ticket t: tickets) {
            if (t.getNumber() == 0) continue;
            Flight flight = flightRepository.getByNumber(t.getFlightnumber());
            int diff = (int) ((flight.getDeparturetime().getTime() - currDate.getTime()) / (1000 * 60 * 60 * 24));
            if (diff < 1) continue;
            result.add(t);
        }
        return result;
    }

    @GetMapping("/getBTickets/{id}")
    public List<Ticket> getBTickets(@PathVariable int id) {
        List<Ticket> tickets = ticketRepository.findByUserid(id);
        List<Ticket> result = new ArrayList<>();
        Date currDate = new Date();
        for (Ticket t: tickets) {
            if (t.getNumber() == 0) continue;
            Flight flight = flightRepository.getByNumber(t.getFlightnumber());
            int diff = (int) ((flight.getDeparturetime().getTime() - currDate.getTime()) / (1000 * 60 * 60));
            if (diff > 72 || diff < 8) continue;
            result.add(t);
        }
        return result;
    }

    @PostMapping("/buy")
    public String buy(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            int id = Integer.valueOf(jsonObject.getString("id"));
            String fnumber = jsonObject.getString("fnumber");
            int cardNum = Integer.valueOf(jsonObject.getString("cardNum"));
            Price price = priceRepository.findByFnumber(fnumber);
            if (price == null) return "{\"status\":\"FAIL\"}";
            Ticket ticket = new Ticket();
            ticket.setUserid(id);
            ticket.setFlightnumber(fnumber);
            ticket.setNumber(cardNum);
            ticket.setPrice(price.getPrice() * cardNum);
            ticketRepository.save(ticket);
            ticketRepository.flush();
            purchaseMail(fnumber, id, cardNum, price.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"status\":\"FAIL\"}";
        }
        return "{\"status\":\"OK\"}";
    }

    private String purchaseMail(String fnumber, int id, int cardNum, int price) {
        SimpleMailMessage msg = new SimpleMailMessage();
        Flight flight = flightRepository.getByNumber(fnumber);
        User user = userRepository.findOne(id);
        Destination start = destinationRepository.findByCode(flight.getFrom_destination());
        Destination end = destinationRepository.findByCode(flight.getTo_destination());

        String text = "Postovani " + user.getFirstName() + ",\n" +
                "Uspesno ste kupili " + cardNum + " kartu/e. Pregled Vase narudzbine:\n" +
                "Polecete iz: " + start.getCity() + ", " + start.getCountry() + "\n" +
                "Odrediste: " + end.getCity() + ", " + end.getCountry() + "\n" +
                "Vreme polaska: " + flight.getDeparturetime() + "\n" +
                "Cena karte: " + price + "\n" +
                "Ukupna cena: " + price * cardNum + "\n\n" +
                "Hvala na poverenju.";

        msg.setSubject("Kupljene karte");
        msg.setText(text);
        msg.setTo(user.getUsername());
        javaMailSender.send(msg);

        return "OK";
    }

    @PostMapping("/returnTicket")
    public String returnTicket(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            int id = jsonObject.getInt("tid");
            Ticket ticket = ticketRepository.findOne(id);
            if (ticket == null) return "{\"status\":\"FAIL\"}";
            ticketRepository.delete(ticket);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"status\":\"FAIL\"}";
        }
        return "{\"status\":\"OK\"}";
    }

    @PostMapping("/history")
    public List<History> getHistory(HttpServletRequest req) {
        List<History> result = new ArrayList<>();
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            int userId = jsonObject.getInt("userId");
            int srow;
            int scol;
            int price;
            int distance = 0;
            String destination;
            List<Ticket> tickets = ticketRepository.findByUserid(userId);
            for (Ticket t: tickets) {
                Flight f = flightRepository.getByNumber(t.getFlightnumber());
                price = t.getPrice();
                if (f == null) return null;
                Date date = f.getDeparturetime();
                String destCode = f.getTo_destination();
                Destination toDestination = destinationRepository.findByCode(destCode);
                if (toDestination == null) return null;
                destination = toDestination.getCity();
                List<BoardingTicket> bts = boardingTicketRepository.findByTicketid(t.getId());
                if (bts.size() == 0) {
                    srow = -1;
                    scol = -1;
                    distance =  0;
                    History newHistory = new History(destination, srow, scol, date, price, distance);
                    result.add(newHistory);
                } else {
                    price = price/(bts.size());
                    distance = f.getDistance();
                    for (BoardingTicket bt: bts) {
                        srow = bt.getRow();
                        scol = bt.getCol();
                        History newHistory = new History(destination, srow, scol, date, price, distance);
                        result.add(newHistory);
                        distance = 0;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
