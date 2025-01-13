package com.travelport.projecttwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Project Two", version = "v1"))
@SpringBootApplication
public class ProjectTwoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectTwoApplication.class, args);
  }

}
