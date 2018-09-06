package airline.entites;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "price_list")
public class Price extends BasicEntity implements Serializable {
    private String fnumber;
    private int price;

    public Price() {
    }

    public String getFnumber() {
        return fnumber;
    }

    public void setFnumber(String flight_number) {
        this.fnumber = flight_number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
