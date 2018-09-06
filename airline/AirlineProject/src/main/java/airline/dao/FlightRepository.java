package airline.dao;

import airline.entites.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    public Flight getByNumber(String number);
    public List<Flight> findByDeparturetimeAfter(Date date);
    public List<Flight> getByAirplaneid(int id);
}
