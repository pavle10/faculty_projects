package airline.dao;

import airline.entites.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {

    public Destination findByCode(String code);
}
