package com.travelport.projecttwo.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ProductRequest {

  @NotNull
  @Size(min = 2, max = 100)
  private String name;

  @NotNull
  @Size(min = 5, max = 10)
  private String code;

  public @NotNull @Size(min = 2, max = 100) String getName() {
    return name;
  }

  public void setName(@NotNull @Size(min = 2, max = 100) String name) {
    this.name = name;
  }

  public @NotNull @Size(min = 5, max = 10) String getCode() {
    return code;
  }

  public void setCode(@NotNull @Size(min = 5, max = 10) String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductRequest that)) return false;
    return Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(code);
  }

}
