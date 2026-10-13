package com.example.billcalc.controller;

import com.example.billcalc.dto.*;
import com.example.billcalc.service.BillService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BillController {

    private final BillService service;

    public BillController(BillService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public BillResponse calculate(@Valid @RequestBody BillRequest request) {
        return service.calculate(request.units());
    }

    @GetMapping("/slabs")
    public SlabsResponse slabs() {
        return service.getSlabs();
    }

    @GetMapping("/history")
    public List<HistoryItem> history() {
        return service.getHistory();
    }

    @DeleteMapping("/history")
    public ResponseEntity<Void> clearHistory() {
        service.clearHistory();
        return ResponseEntity.noContent().build();
    }
}
