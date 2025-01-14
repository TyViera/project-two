package com.travelport.projecttwo.repository.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "purchases_cab")
public class PurchaseCabEntity {

    @Id
    @Length(min = 36, max = 36)
    @NotBlank
    private String id;

    @NotBlank
    @Length(max = 50)
    private String supplier;

    @OneToMany(mappedBy = "purchaseCab", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseDetEntity> details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<PurchaseDetEntity> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseDetEntity> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PurchaseCabEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
