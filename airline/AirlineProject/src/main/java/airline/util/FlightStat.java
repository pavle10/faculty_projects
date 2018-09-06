package airline.util;

import java.util.Date;

public class FlightStat {
    private String fnumber;
    private int airplaneId;
    private String fromDest;
    private String toDest;
    private Date depDate;
    private Date arrDate;
    private double fTime;
    private int distance;
    private String gate;
    private int numOfPass;

    public FlightStat() {
    }

    public String getFnumber() {
        return fnumber;
    }

    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    public int getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(int airplaneId) {
        this.airplaneId = airplaneId;
    }

    public String getFromDest() {
        return fromDest;
    }

    public void setFromDest(String fromDest) {
        this.fromDest = fromDest;
    }

    public String getToDest() {
        return toDest;
    }

    public void setToDest(String toDest) {
        this.toDest = toDest;
    }

    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    public Date getArrDate() {
        return arrDate;
    }

    public void setArrDate(Date arrDate) {
        this.arrDate = arrDate;
    }

    public double getfTime() {
        return fTime;
    }

    public void setfTime(double fTime) {
        this.fTime = fTime;
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

    public int getNumOfPass() {
        return numOfPass;
    }

    public void setNumOfPass(int numOfPass) {
        this.numOfPass = numOfPass;
    }
}
