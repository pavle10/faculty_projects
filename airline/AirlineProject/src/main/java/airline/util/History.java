package airline.util;

import java.util.Date;

public class History {
    private String destination;
    private int srow;
    private int scol;
    private Date fdate;
    private int price;
    private int distance;

    public History(String destination, int row, int col, Date date, int price, int distance) {
        this.destination = destination;
        this.srow = row;
        this.scol = col;
        this.fdate = date;
        this.price = price;
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getSRow() {
        return srow;
    }

    public void setSRow(int row) {
        this.srow = row;
    }

    public int getSCol() {
        return scol;
    }

    public void setSCol(int col) {
        this.scol = col;
    }

    public Date getFDate() {
        return fdate;
    }

    public void setFDate(Date date) {
        this.fdate = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
