package com.example.MessengerApp.mappers;

import com.example.MessengerApp.dto.UserDTO;
import com.example.MessengerApp.model.User;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-13T03:16:28+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.17 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setIdDto( user.getId() );
        userDTO.setUsernameDto( user.getUsername() );
        userDTO.setPasswordDto( user.getPassword() );
        userDTO.setOnline( user.getOnline() );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getIdDto() );
        user.setUsername( userDTO.getUsernameDto() );
        user.setPassword( userDTO.getPasswordDto() );
        user.setOnline( userDTO.getOnline() );

        return user;
    }
}
