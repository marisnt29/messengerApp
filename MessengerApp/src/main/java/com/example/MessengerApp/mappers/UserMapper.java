package com.example.MessengerApp.mappers;

import com.example.MessengerApp.dto.UserDTO;
import com.example.MessengerApp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {


    @Mapping(source = "user.id", target = "idDto")
    @Mapping(source = "user.username", target = "usernameDto")
    @Mapping(source = "user.password", target = "passwordDto")
    @Mapping(source = "user.online", target = "online")
    UserDTO toDTO(User user);


    @Mapping(source = "idDto", target = "id")
    @Mapping(source = "usernameDto", target = "username")
    @Mapping(source = "passwordDto", target = "password")
    @Mapping(source = "userDTO.online", target = "online")
    User toEntity(UserDTO userDTO);

}