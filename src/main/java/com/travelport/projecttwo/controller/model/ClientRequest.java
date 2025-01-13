package com.travelport.projecttwo.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ClientRequest {

  @NotNull
  @Size(min = 2, max = 150)
  public String name;

  @NotNull
  @Size(min = 9, max = 10)
  public String nif;

  @Size(min = 5, max = 150)
  public String address;

  public @NotNull @Size(min = 2, max = 150) String getName() {
    return name;
  }

  public void setName(@NotNull @Size(min = 2, max = 150) String name) {
    this.name = name;
  }

  public @NotNull @Size(min = 9, max = 10) String getNif() {
    return nif;
  }

  public void setNif(@NotNull @Size(min = 9, max = 10) String nif) {
    this.nif = nif;
  }

  public @Size(min = 5, max = 150) String getAddress() {
    return address;
  }

  public void setAddress(@Size(min = 5, max = 150) String address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ClientRequest that)) return false;
    return Objects.equals(nif, that.nif);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(nif);
  }
}
