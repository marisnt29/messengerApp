package com.example.MessengerApp.controllers;

import com.example.MessengerApp.dto.UserDTO;
import com.example.MessengerApp.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;


    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Returns a list of all users")
    @GetMapping("/")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Return a user based on his id")
    @GetMapping("/{id}")
    public UserDTO getUserById(@ApiParam("Id of the user you want to retrieve") @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @ApiOperation(value = "Register an user")
    @PostMapping("/register")
    public ResponseEntity saveUser(@ApiParam("Body of an user") @RequestBody UserDTO user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(user));

    }

    @ApiOperation(value = "Update an user")
    @PutMapping("/{id}")
    public void updateUser(@ApiParam("Body of an user with modifications") @RequestBody UserDTO user, @ApiParam("The id of the user  you want to modify") @PathVariable Long id) {
        user.setIdDto(id);
        userService.updateUser(user);
    }

    @ApiOperation(value = "Logs out an user / Changes status to offline")
    @PutMapping("/logout/{id}")
    public void logout(@ApiParam("Body the user with moddified status") @RequestBody UserDTO user, @ApiParam("The id of the user  you want to modify") @PathVariable Long id) {
        user.setIdDto(id);
        userService.logout(user);
    }



    @ApiOperation(value = "Delete an user")
    @DeleteMapping("/{id}")
    public void deleteUser(@ApiParam("id of the user you want to delete") @PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        userService.deleteUser(user);
    }

    @ApiOperation(value = "Logins an user and returns response entity")
    @PostMapping("/login")
    public ResponseEntity login(@ApiParam("Body of  user with credentials you want to login") @RequestBody UserDTO user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(user.getUsernameDto(), user.getPasswordDto()));
    }

    @GetMapping("/export/{userID}")
    public ResponseEntity exportUserDetails(@PathVariable Long userID, @RequestParam String fileType) {
        return ResponseEntity.ok(userService.exportUserDetails(userID, fileType));
    }

}