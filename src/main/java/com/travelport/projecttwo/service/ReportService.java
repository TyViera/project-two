package com.travelport.projecttwo.service;

import com.travelport.projecttwo.dto.MostSoldProductDTO;
import com.travelport.projecttwo.entity.SaleItem;
import com.travelport.projecttwo.repository.SaleItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final SaleItemRepository saleItemRepository;

    public ReportService(SaleItemRepository saleItemRepository) {
        this.saleItemRepository = saleItemRepository;
    }

    public List<MostSoldProductDTO> getMostSoldProducts() {
        return saleItemRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        item -> item.getProduct(),
                        Collectors.summingInt(SaleItem::getQuantity)
                ))
                .entrySet().stream()
                .map(entry -> {
                    MostSoldProductDTO dto = new MostSoldProductDTO();
                    dto.setProductId(entry.getKey().getId());
                    dto.setProductName(entry.getKey().getName());
                    dto.setQuantitySold(entry.getValue());
                    return dto;
                })
                .sorted((a, b) -> Integer.compare(b.getQuantitySold(), a.getQuantitySold()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
