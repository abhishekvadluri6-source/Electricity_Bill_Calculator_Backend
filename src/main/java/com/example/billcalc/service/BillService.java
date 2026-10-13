package com.example.billcalc.service;

import com.example.billcalc.dto.*;
import com.example.billcalc.model.BillRecord;
import com.example.billcalc.model.SlabRate;
import com.example.billcalc.repository.BillRecordRepository;
import com.example.billcalc.repository.SlabRateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillService {

    private final SlabRateRepository slabRepository;
    private final BillRecordRepository billRepository;
    private final BigDecimal serviceCharge;

    public BillService(SlabRateRepository slabRepository,
                       BillRecordRepository billRepository,
                       @Value("${billing.service-charge}") BigDecimal serviceCharge) {
        this.slabRepository = slabRepository;
        this.billRepository = billRepository;
        this.serviceCharge = serviceCharge.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Progressive slabs: every unit is charged at the rate of the slab it falls in.
     * The result is saved to MySQL so it shows up in the history list.
     */
    @Transactional
    public BillResponse calculate(BigDecimal units) {
        List<SlabRate> slabs = slabRepository.findAllByOrderBySlabOrderAsc();
        List<BillLine> lines = new ArrayList<>();
        BigDecimal remaining = units;
        BigDecimal energy = BigDecimal.ZERO;
        int previousLimit = 0;

        for (SlabRate slab : slabs) {
            if (remaining.signum() <= 0) {
                break;
            }
            BigDecimal used = (slab.getUpToUnits() == null)
                    ? remaining
                    : remaining.min(BigDecimal.valueOf(slab.getUpToUnits() - previousLimit));
            BigDecimal amount = used.multiply(slab.getRatePerUnit()).setScale(2, RoundingMode.HALF_UP);

            lines.add(new BillLine(label(slab, previousLimit), used, slab.getRatePerUnit(), amount));
            energy = energy.add(amount);
            remaining = remaining.subtract(used);
            if (slab.getUpToUnits() != null) {
                previousLimit = slab.getUpToUnits();
            }
        }

        BigDecimal total = energy.add(serviceCharge).setScale(2, RoundingMode.HALF_UP);
        billRepository.save(new BillRecord(units, energy, serviceCharge, total));
        return new BillResponse(units, lines, energy, serviceCharge, total);
    }

    @Transactional(readOnly = true)
    public SlabsResponse getSlabs() {
        List<SlabInfo> infos = new ArrayList<>();
        int previousLimit = 0;
        for (SlabRate slab : slabRepository.findAllByOrderBySlabOrderAsc()) {
            infos.add(new SlabInfo(label(slab, previousLimit), slab.getRatePerUnit()));
            if (slab.getUpToUnits() != null) {
                previousLimit = slab.getUpToUnits();
            }
        }
        return new SlabsResponse(serviceCharge, infos);
    }

    @Transactional(readOnly = true)
    public List<HistoryItem> getHistory() {
        return billRepository.findTop8ByOrderByCreatedAtDesc().stream()
                .map(b -> new HistoryItem(b.getId(), b.getUnits(), b.getTotalAmount(), b.getCreatedAt()))
                .toList();
    }

    @Transactional
    public void clearHistory() {
        billRepository.deleteAll();
    }

    private static String label(SlabRate slab, int previousLimit) {
        if (slab.getUpToUnits() == null) {
            return "Above " + previousLimit;
        }
        int from = (previousLimit == 0) ? 0 : previousLimit + 1;
        return from + " \u2013 " + slab.getUpToUnits();
    }
}
