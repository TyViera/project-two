package com.travelport.projecttwo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Schema
@Entity
public class Client {

    @Id
    @Schema( description = "Client ID")
    private UUID clientId = UUID.randomUUID();

    @NotBlank
    @Length(min = 2, max = 150)
    @Schema(description = "Client Name")
    private String name;

    @NotBlank
    @Length(min = 9, max = 10)
    @Schema()
    private String nif;

    @Length(min = 5, max = 150)
    @Schema()
    private String address;

    public UUID getClientId() {return clientId;}
    public void setClientId(UUID clientId) {this.clientId = clientId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getNif() {return nif;}
    public void setNif(String nif) {this.nif = nif;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
}
