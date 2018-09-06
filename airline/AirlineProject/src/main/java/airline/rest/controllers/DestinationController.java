package airline.rest.controllers;

import airline.dao.DestinationRepository;
import airline.entites.Destination;
import airline.util.JSONUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/destination")
@CrossOrigin(origins = "*")
public class DestinationController {

    @Autowired
    protected DestinationRepository destinationRepository;

    @GetMapping("/getAll")
    public List<Destination> getAll() {
        List<Destination> destinations = destinationRepository.findAll();
        return destinations;
    }


    @PostMapping("/add")
    public String addDestination(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String code = jsonObject.getString("code");
            String city = jsonObject.getString("city");
            String country = jsonObject.getString("country");
            int time_zone = Integer.valueOf(jsonObject.getString("timezone"));
            Destination dest = destinationRepository.findByCode(code);
            if (dest != null) return "{\"status\":\"CODE\"}";
            Destination destination = new Destination();
            destination.setCode(code);
            destination.setCity(city);
            destination.setCountry(country);
            destination.setTime_zone(time_zone);
            destinationRepository.save(destination);
        } catch (IOException e) {
            return "{\"status\":\"FAIL\"}";
        }
        return "{\"status\":\"OK\"}";
    }

    @PostMapping("/remove")
    public String removeDestination(HttpServletRequest req) {
        try {
            JSONObject jsonObject = JSONUtil.requestToJSON(req);
            String code = jsonObject.getString("code");
            Destination destination = destinationRepository.findByCode(code);
            if (destination == null) return "{\"status\":\"FAIL\"}";
            destinationRepository.delete(destination);
        } catch (IOException e) {
            return "{\"status\":\"FAIL\"}";
        }
        return "{\"status\":\"OK\"}";
    }
}
