package com.travelport.projecttwo.entities;

import jakarta.persistence.*;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clients")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, length = 150)
  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
  private String name;

  @Column(nullable = false, unique = true, length = 10)
  @NotBlank(message = "NIF is mandatory")
  @Size(min = 9, max = 10, message = "NIF must be between 9 and 10 characters")
  private String nif;

  @Column(length = 150)
  @Size(min = 5, max = 150, message = "Address must be between 5 and 150 characters")
  private String address;



  public Client() {
  }

  public Client(String name, String nif, String address) {
    this.name = name;
    this.nif = nif;
    this.address = address;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
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
  public String toString() {
    return "Client{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", nif='" + nif + '\'' +
        (address != null ? ", address='" + address + '\'' : "") +
        '}';
  }
}