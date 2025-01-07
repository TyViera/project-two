package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController//TODO: maybe this could be only @Controller
@RequestMapping("/clients")
public class ClientController {

    // TODO: catch errors from the service layer and transform them into http responses
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientService.getClients(), HttpStatus.OK);
    }

   @GetMapping("/{id}")
    public ResponseEntity<Object> findById( @Parameter(name = "id", description = "ID of client to return", required = true, in = ParameterIn.PATH) @PathVariable("id") String id){
        Client client;
        try{
            client = clientService.getClientById(id);

        }catch(NoSuchElementException e) {
            return new ResponseEntity<>(
                    "Client not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                client,
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Object> postClient(@RequestBody Client client){ // TODO: not working
        try {
            Client newClient=clientService.addClient(client);
            return new ResponseEntity<>(
                    newClient,
                    HttpStatus.CREATED
            );
        }catch(Exception e){
            return new ResponseEntity<>(
                    "Client not saved",
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClientById(@PathVariable("id") String id, @RequestBody Client client){
        try {
            var clientUpdated=clientService.updateClient(id, client);
            return new ResponseEntity<>(
                    clientUpdated,
                    HttpStatus.OK
            );
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(
                    "Client not found",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClientById(@PathVariable("id") String id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
