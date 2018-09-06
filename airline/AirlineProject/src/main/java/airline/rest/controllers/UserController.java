package airline.rest.controllers;

import airline.dao.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import airline.entites.User;
import airline.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected JavaMailSender javaMailSender;

    @GetMapping("/test")
    public String test() {
        return "Radi li Spring Boot?!";
    }

    public void setCorsHeaders(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        res.setHeader("Access-Control-Max-Age", "1209600");
    }

    @PostMapping("/getUser")
    public String getUser(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            int userId = jsonObject.getInt("userId");
            User user =  userRepository.findOne(userId);
            if (user == null) return "{\"status\":\"FAIL\"}";
            String msg = "{\"status\":\"OK\", ";
            msg += "\"name\":\"" + user.getFirstName() + "\", ";
            msg += "\"type\":\"" + user.getType() + "\", ";
            msg += "\"email\":\"" + user.getUsername() + "\"} ";
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"status\":\"FAIL\"}";
        }
    }

    @RequestMapping(value="/register", method = RequestMethod.OPTIONS)
    public void registerOptions(HttpServletResponse res) {
       setCorsHeaders(res);
    }

    @PostMapping("/register")
    public String addUser(HttpServletRequest req, HttpServletResponse res) {
        setCorsHeaders(res);
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String fname = jsonObject.getString("fname");
            String lname = jsonObject.getString("lname");
            String username = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            User temp = userRepository.findByUsername(username);
            if (temp != null) return "{\"status\":\"EXIST\"}";
            User user = new User();
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setUsername(username);
            user.setPassword(DigestUtils.sha1Hex(password));
            user.setType(3);
            user.setRegistred(0);
            userRepository.save(user);
            System.out.println(activateMail(username));
            return "{\"status\":\"OK\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"FAIL\"}";
        }

    }

    @RequestMapping(value="/login", method = RequestMethod.OPTIONS)
    public void loginOptions(HttpServletResponse res) {
        setCorsHeaders(res);
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse res){
        setCorsHeaders(res);
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            User user = userRepository.findByUsername(username);
            if (user == null) return "{\"status\":\"FAIL\"}";
            if (!user.getPassword().equals(DigestUtils.sha1Hex(password))) return "{\"status\":\"FAIL\"}";
            String msg = "{\"status\":\"OK\", ";
            msg += "\"user\":\"" + user.getUsername() + "\", ";
            msg += "\"id\":\"" + user.getId() + "\", ";
            msg += "\"type\":\"" + user.getType() + "\", ";
            if (user.getFirstName() != null) msg += "\"name\":\"" + user.getFirstName() + "\", ";
            msg += "\"reg\":\"" + user.getRegistred() + "\"}";
            return msg;
        } catch (IOException e) {
            return "{\"status\":\"FAIL\"}";
        }
    }

    @PostMapping("/addAdmin")
    public String addAdmin(HttpServletRequest req, HttpServletResponse res) {
        setCorsHeaders(res);
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String fname = jsonObject.getString("fname");
            String lname = jsonObject.getString("lname");
            String username = jsonObject.getString("username");
            String password = "sifra123";
            String type = jsonObject.getString("type");
            User temp = userRepository.findByUsername(username);
            if (temp != null) return "{\"status\":\"FAIL\"}";
            User newUser = new User();
            newUser.setFirstName(fname);
            newUser.setLastName(lname);
            newUser.setUsername(username);
            newUser.setPassword(DigestUtils.sha1Hex(password));
            newUser.setType(Integer.valueOf(type));
            newUser.setRegistred(0);
            userRepository.save(newUser);
        } catch (IOException e) {
            return "{\"status\":\"FAIL\"}";
        }
        return "{\"status\":\"OK\"}";
    }

    @PostMapping("/changePass")
    public String changePass(HttpServletRequest req, HttpServletResponse res) {
        setCorsHeaders(res);
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String id = jsonObject.getString("id");
            String password = jsonObject.getString("password");
            User user = userRepository.findOne(Integer.valueOf(id));
            if (user == null) return "{\"status\":\"FAIL\"}";
            user.setPassword(DigestUtils.sha1Hex(password));
            user.setRegistred(1);
            userRepository.save(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\":\"OK\"}";
    }

    @PostMapping("/removeAdmin")
    public String removeAdmin(HttpServletRequest req, HttpServletResponse res) {
        setCorsHeaders(res);
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String username = jsonObject.getString("username");
            User user = userRepository.findByUsername(username);
            if (user == null) return "{\"status\":\"FAIL\"}";
            if (user.getType() == 3) return "{\"status\":\"TYPE\"}";
            userRepository.delete(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\":\"OK\"}";
    }

    private String activateMail(String mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String text = "Uspesno ste se registrovali, molimo Vas" +
                    "kako biste aktivirali nalog kliknite na sledeci link " +
                    "http://localhost:8080/user/activate/" + mail;
        msg.setSubject("Aktivacioni mail");
        msg.setText(text);
        msg.setTo(mail);
        javaMailSender.send(msg);

        return "OK";
    }

    @RequestMapping(
            value = "/activate/{email:.+}",
            method = RequestMethod.GET)
    public String activate(@PathVariable String email) {
        System.out.println(email);
        User user = userRepository.findByUsername(email);
        if (user == null) return "Neuspesno!";
        user.setRegistred(1);
        userRepository.save(user);
        return "Uspesno aktiviranje naloga";
    }

}
