package airline.entites;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ticket")
public class Ticket extends BasicEntity implements Serializable {
    private int userid;
    private String flightnumber;
    private int number;
    private int price;

    public Ticket() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFlightnumber() {
        return flightnumber;
    }

    public void setFlightnumber(String flightnumber) {
        this.flightnumber = flightnumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
