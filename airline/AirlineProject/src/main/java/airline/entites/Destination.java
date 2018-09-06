package airline.entites;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="destination")
public class Destination extends BasicEntity implements Serializable {
    private String code;
    private String city;
    private String country;
    private int time_zone;

    public Destination() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(int time_zone) {
        this.time_zone = time_zone;
    }
}
