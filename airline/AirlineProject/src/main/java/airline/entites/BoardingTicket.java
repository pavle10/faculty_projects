package airline.entites;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "boarding_ticket")
public class BoardingTicket extends BasicEntity implements Serializable {
    private int ticketid;
    private String fnumber;
    private String pfirstname;
    private String plastname;
    private String jmbg;
    private int row;
    private int col;

    public BoardingTicket() {
    }

    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    public String getFnumber() {
        return fnumber;
    }

    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    public String getPfirstname() {
        return pfirstname;
    }

    public void setPfirstname(String pfirstname) {
        this.pfirstname = pfirstname;
    }

    public String getPlastname() {
        return plastname;
    }

    public void setPlastname(String plastname) {
        this.plastname = plastname;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
