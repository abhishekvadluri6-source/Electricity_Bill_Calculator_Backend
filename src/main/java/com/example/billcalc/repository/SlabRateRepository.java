package com.example.billcalc.repository;

import com.example.billcalc.model.SlabRate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SlabRateRepository extends JpaRepository<SlabRate, Long> {
    List<SlabRate> findAllByOrderBySlabOrderAsc();
}
