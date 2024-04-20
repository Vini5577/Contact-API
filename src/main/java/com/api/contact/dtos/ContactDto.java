package com.api.contact.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

}
