package com.test.backend.repository;

import com.test.backend.domain.Bus;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {

    Optional<Bus> findByBusNumber(Integer busNumber);
    List<Bus>  findByBusNumberIn(Set<Integer> busNumbers);
}
