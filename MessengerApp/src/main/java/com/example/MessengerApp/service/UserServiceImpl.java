package com.example.MessengerApp.service;

import com.example.MessengerApp.dto.UserDTO;
import com.example.MessengerApp.exporter.FileExporter;
import com.example.MessengerApp.exporter.TXTFileExporter;
import com.example.MessengerApp.exporter.XMLFileExporter;
import com.example.MessengerApp.mappers.UserMapper;
import com.example.MessengerApp.model.Conversation;
import com.example.MessengerApp.model.User;
import com.example.MessengerApp.repository.ConversationRepository;
import com.example.MessengerApp.repository.UserRepository;
import com.example.MessengerApp.validators.PasswordValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ConversationRepository conversationRepository;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        return mapper.toDTO(user);
    }

    @Override
    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid user credentials:"));

        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"PASSWORD DON'T MATCH!");
        }


        return mapper.toDTO(user);
    }

    @Override
    public String exportUserDetails(Long userID, String fileType) {
        User user = userRepository.findById(userID).orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + userID));
        FileExporter fileExporter;
        if (fileType.equals("xml")) {
            fileExporter = new XMLFileExporter();
            return fileExporter.exportData(user);
        } else if (fileType.equals("txt")) {
            fileExporter = new TXTFileExporter();
            return fileExporter.exportData(user);
        }
        return null;
    }

    @Override
    public UserDTO saveUser(UserDTO user) {

        if (PasswordValidator.isValid(user.getPasswordDto())) {
            User newUser = new User(user.getIdDto(), user.getUsernameDto(), user.getPasswordDto(), false);

            String encryptedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
            newUser.setPassword(encryptedPassword);
            userRepository.save(newUser);
            return mapper.toDTO(newUser);
        } else throw new IllegalArgumentException("Invalid password!");

    }

    @Override
    public void updateUser(UserDTO user) {
        User existingUser = userRepository.findById(user.getIdDto()).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user.getIdDto()));
        existingUser.setUsername(user.getUsernameDto());
        existingUser.setPassword(user.getPasswordDto());
        existingUser.setOnline(user.getOnline());
        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UserDTO user) {
        User existingUser = userRepository.findById(user.getIdDto()).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user.getIdDto()));
        List<Conversation> userConversations = new ArrayList<>();
        userConversations = conversationRepository.findAll().stream().filter(conv -> conv.getUsers().contains(existingUser)).collect(Collectors.toList());
        for (Conversation conversation : userConversations) {
            conversation.getUsers().remove(existingUser);
            if (conversation.getUsers().size() < 2) {
                conversationRepository.delete(conversation);
            } else {
                conversationRepository.save(conversation);
            }
        }

        userRepository.delete(existingUser);

    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTOs = new ArrayList<>();
        for (User user : users) {
            usersDTOs.add(mapper.toDTO(user));
        }
        return usersDTOs;
    }


    //just an update with new status
    public void logout(UserDTO user) {
        User existingUser = userRepository.findById(user.getIdDto()).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user.getIdDto()));
        existingUser.setUsername(user.getUsernameDto());
        existingUser.setPassword(user.getPasswordDto());
        existingUser.setOnline(Boolean.FALSE);

        userRepository.save(existingUser);
    }


}
