package com.example.billcalc.repository;

import com.example.billcalc.model.BillRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillRecordRepository extends JpaRepository<BillRecord, Long> {
    List<BillRecord> findTop8ByOrderByCreatedAtDesc();
}
