package airline.dao;

import airline.entites.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    public List<Ticket> findByUserid(int id);
    public List<Ticket> findByFlightnumberAndAndNumberGreaterThan(String fnumber, int number);
}
