package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Response.SaleHistoryResponse;
import java.util.List;

public interface SaleHistoryService {

    List<SaleHistoryResponse> getSalesHistoryByClientId(String clientId);
}
