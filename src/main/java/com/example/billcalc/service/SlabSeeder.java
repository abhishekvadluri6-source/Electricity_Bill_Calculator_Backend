package com.example.billcalc.service;

import com.example.billcalc.model.SlabRate;
import com.example.billcalc.repository.SlabRateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Inserts the example slab rates from the project poster the first time the app starts. */
@Component
public class SlabSeeder implements CommandLineRunner {

    private final SlabRateRepository repository;

    public SlabSeeder(SlabRateRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.saveAll(List.of(
                    new SlabRate(1, 100, new BigDecimal("2.50")),
                    new SlabRate(2, 200, new BigDecimal("4.00")),
                    new SlabRate(3, 300, new BigDecimal("6.00")),
                    new SlabRate(4, null, new BigDecimal("8.00"))));
        }
    }
}
