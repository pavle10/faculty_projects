package airline.entites;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "flight")
public class Flight extends BasicEntity implements Serializable {

    private String number;
    private int airplaneid;
    private String from_destination;
    private String to_destination;
    private Date departuretime;
    private Date arrivaltime;
    private double flight_time;
    private int distance;
    private String gate;

    public Flight() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAirplane_id() {
        return airplaneid;
    }

    public void setAirplane_id(int airplane_id) {
        this.airplaneid = airplane_id;
    }

    public String getFrom_destination() {
        return from_destination;
    }

    public void setFrom_destination(String from_destination) {
        this.from_destination = from_destination;
    }

    public String getTo_destination() {
        return to_destination;
    }

    public void setTo_destination(String to_destination) {
        this.to_destination = to_destination;
    }

    public Date getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(Date departure_time) {
        this.departuretime = departure_time;
    }

    public Date getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(Date arrival_time) {
        this.arrivaltime = arrival_time;
    }

    public double getFlight_time() {
        return flight_time;
    }

    public void setFlight_time(double flight_time) {
        this.flight_time = flight_time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }
}
