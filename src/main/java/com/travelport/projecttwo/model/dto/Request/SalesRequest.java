package com.travelport.projecttwo.model.dto.Request;

import java.util.Map;
import java.util.UUID;

public class SalesRequest {
    UUID clientId;
    Map<UUID, Integer> products;

    public UUID getClientId() { return clientId; }

    public Map<UUID, Integer> getProducts() { return products; }
}
