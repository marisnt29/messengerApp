package com.example.MessengerApp.service;

import com.example.MessengerApp.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO saveUser(UserDTO user);
    void updateUser(UserDTO user);
    void deleteUser(UserDTO user);
    List<UserDTO> getAllUsers();
    UserDTO login(String username, String password);

    String exportUserDetails(Long userID, String fileType);

}
