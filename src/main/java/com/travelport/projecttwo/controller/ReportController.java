package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.MostSoldProductDTO;
import com.travelport.projecttwo.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/most-sold-products")
    public List<MostSoldProductDTO> getMostSoldProducts() {
        return reportService.getMostSoldProducts();
    }
}
