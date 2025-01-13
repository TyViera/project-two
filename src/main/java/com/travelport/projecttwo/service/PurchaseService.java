package com.travelport.projecttwo.service;

import com.travelport.projecttwo.controller.model.PurchaseRequest;
import com.travelport.projecttwo.exception.ProductNotFoundException;

public interface PurchaseService {

  public void renewStock (PurchaseRequest purchase) throws ProductNotFoundException;

}
