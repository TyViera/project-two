package com.travelport.projecttwo.repository.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelport.projecttwo.controller.model.ClientRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clients")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientEntity {

  public ClientEntity() {}

  public ClientEntity(ClientRequest inputClient) {
    this.id = UUID.randomUUID().toString();
    this.name = inputClient.getName();
    this.nif = inputClient.getNif();
    this.address = inputClient.getAddress();
  }

  @Id
  public String id;

  @Column(name ="name", length = 150, nullable = false)
  public String name;

  @Column(name ="nif", length = 10, nullable = false)
  public String nif;

  @Column(name ="address", length = 150, nullable = true)
  public String address;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNif() {
    return nif;
  }

  public void setNif(String nif) {
    this.nif = nif;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ClientEntity client)) return false;
    return Objects.equals(id, client.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
