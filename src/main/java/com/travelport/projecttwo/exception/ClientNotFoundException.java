package com.travelport.projecttwo.exception;

public class ClientNotFoundException extends SaleException {

  public ClientNotFoundException(String clientId) {
    super("Client with id " + clientId + " not found");
  }

}
