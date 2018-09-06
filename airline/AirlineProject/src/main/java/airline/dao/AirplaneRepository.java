package airline.dao;

import airline.entites.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {

    public Airplane findByRegistration(String registration);
}
