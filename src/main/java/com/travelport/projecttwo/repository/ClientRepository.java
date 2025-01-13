package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {

  public List<ClientEntity> findAllByOrderByName ();

}
