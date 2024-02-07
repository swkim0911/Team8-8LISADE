package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c join fetch c.stations")
    List<City> findAllWithStation();
}
