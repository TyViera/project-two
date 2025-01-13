package com.travelport.projecttwo.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class Error {

    @Schema(
            description = "Error message",
            example = "Something failed due to previous errors, check logs.")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

