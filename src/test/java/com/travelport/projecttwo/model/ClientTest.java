package com.travelport.projecttwo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void getId() {
        Client client = new Client();
        client.setId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", client.getId());
    }

    @Test
    void setId() {
        Client client = new Client();
        client.setId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", client.getId());
    }

    @Test
    void getName() {
        Client client = new Client();
        client.setName("John Doe");
        assertEquals("John Doe", client.getName());
    }

    @Test
    void setName() {
        Client client = new Client();
        client.setName("Jane Doe");
        assertEquals("Jane Doe", client.getName());
    }

    @Test
    void getNif() {
        Client client = new Client();
        client.setNif("123456789");
        assertEquals("123456789", client.getNif());
    }

    @Test
    void setNif() {
        Client client = new Client();
        client.setNif("987654321");
        assertEquals("987654321", client.getNif());
    }

    @Test
    void getAddress() {
        Client client = new Client();
        client.setAddress("123 Main Street, City, Country");
        assertEquals("123 Main Street, City, Country", client.getAddress());
    }

    @Test
    void setAddress() {
        Client client = new Client();
        client.setAddress("456 Elm Street, Another City, Country");
        assertEquals("456 Elm Street, Another City, Country", client.getAddress());
    }
}