package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Response.MostSoldProductResponse;
import java.util.List;

public interface IncomeReportService {

    List<MostSoldProductResponse> getMostSoldProducts();
}
