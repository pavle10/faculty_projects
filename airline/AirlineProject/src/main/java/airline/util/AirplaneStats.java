package airline.util;

public class AirplaneStats {
    private String registration;
    private String brand;
    private String model;
    private int numOfSeats;
    private int cols;
    private int rows;
    private int numCols;
    private int flyDistance;
    private int speed;
    private String flightNumber;
    private int numOfPass;

    public AirplaneStats() {
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

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getFlyDistance() {
        return flyDistance;
    }

    public void setFlyDistance(int flyDistance) {
        this.flyDistance = flyDistance;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getNumOfPass() {
        return numOfPass;
    }

    public void setNumOfPass(int numOfPass) {
        this.numOfPass = numOfPass;
    }
}
