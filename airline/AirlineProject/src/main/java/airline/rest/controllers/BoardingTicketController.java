package airline.rest.controllers;

import airline.dao.BoardingTicketRepository;
import airline.dao.FlightRepository;
import airline.dao.TicketRepository;
import airline.dao.UserRepository;
import airline.entites.*;
import airline.util.JSONUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/boardingTickets")
@CrossOrigin(origins = "*")
public class BoardingTicketController {

    @Autowired
    protected BoardingTicketRepository boardingTicketRepository;
    @Autowired
    protected JavaMailSender javaMailSender;
    @Autowired
    protected TicketRepository ticketRepository;
    @Autowired
    protected FlightRepository flightRepository;
    @Autowired
    protected UserRepository userRepository;

    @GetMapping("/all")
    public List<BoardingTicket> getAll() {
        return boardingTicketRepository.findAll();
    }

    @PostMapping("/byTicket")
    public List<BoardingTicket> getByTicket(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            int ticketId = Integer.valueOf(jsonObject.getString("ticketId"));
            Ticket ticket = ticketRepository.findOne(ticketId);
            if (ticket == null) return null;
            return boardingTicketRepository.findByFnumber(ticket.getFlightnumber());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @PostMapping("/buy")
    public String buy(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            int ticketId = Integer.valueOf(jsonObject.getString("ticketId"));
            String fname = jsonObject.getString("fname");
            String lname = jsonObject.getString("lname");
            String jmbg = jsonObject.getString("jmbg");
            int row = jsonObject.getInt("row");
            int col = jsonObject.getInt("col");
            Ticket ticket = ticketRepository.findOne(ticketId);
            if (ticket == null) return "{\"status\":\"FAIL\"}";
            String fnumber = ticket.getFlightnumber();
            BoardingTicket boardingTicket = new BoardingTicket();
            boardingTicket.setTicketid(ticketId);
            boardingTicket.setFnumber(fnumber);
            boardingTicket.setPfirstname(fname);
            boardingTicket.setPlastname(lname);
            boardingTicket.setJmbg(jmbg);
            boardingTicket.setRow(row);
            boardingTicket.setCol(col);
            boardingTicketRepository.save(boardingTicket);
            boardingTicketRepository.flush();
            int num = ticket.getNumber();
            num -= 1;
            ticket.setNumber(num);
            ticketRepository.save(ticket);
            boardingMail(fname, lname, row, col, fnumber, ticket.getUserid());
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"status\":\"FAIL\"}";
        }
        return "{\"status\":\"OK\"}";
    }

    private String boardingMail(String fname, String lname, int row, int col,
                                String fnumber, int userId) {
        SimpleMailMessage msg = new SimpleMailMessage();
        Flight flight = flightRepository.getByNumber(fnumber);
        User user = userRepository.findOne(userId);

        String text = "Postovani " + user.getFirstName() + ",\n" +
                "Vasa  boarding karta sa sledecim podacima:\n" +
                "Ime: " + fname + "\n" +
                "Prezime: " + lname +"\n" +
                "Let: " + flight.getNumber() + "\n" +
                "Red: " + row + " Kolona: " + col +"\n" +
                "Gate: " + flight.getGate() + "\n\n" +
                "Prijatan let.";

        msg.setSubject("Boarding karta");
        msg.setText(text);
        msg.setTo(user.getUsername());
        javaMailSender.send(msg);

        return "OK";
    }
}
