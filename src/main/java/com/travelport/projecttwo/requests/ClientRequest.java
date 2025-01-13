package com.travelport.projecttwo.requests;

import java.util.UUID;

public class ClientRequest {
  private UUID id;

  public ClientRequest() {
  }

  public ClientRequest(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
