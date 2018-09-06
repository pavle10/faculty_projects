package airline.dao;

import airline.entites.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Integer> {

    public Price findByFnumber(String number);
}
