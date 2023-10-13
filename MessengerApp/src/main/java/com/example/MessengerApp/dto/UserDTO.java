package com.example.MessengerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long idDto;
    private String usernameDto;
    private String passwordDto;
    private Boolean online;

}
