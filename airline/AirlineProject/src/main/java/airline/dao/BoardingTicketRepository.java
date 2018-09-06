package airline.dao;

import airline.entites.BoardingTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardingTicketRepository extends JpaRepository<BoardingTicket, Integer> {

    public List<BoardingTicket> findByFnumber(String fnumber);
    public List<BoardingTicket> findByTicketid(int id);
}
