package airline.entites;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="airplane")
public class Airplane extends BasicEntity implements Serializable {
    private String registration;
    private String brand;
    private String model;
    private int seats_num;
    private int cols_num;
    private int rows_num;
    private int col_seat_num;
    private int fly_distance;
    private int speed;

    public Airplane() {
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeats_num() {
        return seats_num;
    }

    public void setSeats_num(int seats_num) {
        this.seats_num = seats_num;
    }

    public int getCols_num() {
        return cols_num;
    }

    public void setCols_num(int cols_num) {
        this.cols_num = cols_num;
    }

    public int getRows_num() {
        return rows_num;
    }

    public void setRows_num(int rows_num) {
        this.rows_num = rows_num;
    }

    public int getCol_seat_num() {
        return col_seat_num;
    }

    public void setCol_seat_num(int col_seat_num) {
        this.col_seat_num = col_seat_num;
    }

    public int getFly_distance() {
        return fly_distance;
    }

    public void setFly_distance(int fly_distance) {
        this.fly_distance = fly_distance;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
