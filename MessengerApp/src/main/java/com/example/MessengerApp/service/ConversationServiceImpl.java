package com.example.MessengerApp.service;

import com.example.MessengerApp.dto.ConversationDTO;
import com.example.MessengerApp.mappers.ConversationMapper;
import com.example.MessengerApp.model.Conversation;
import com.example.MessengerApp.model.Message;
import com.example.MessengerApp.model.User;
import com.example.MessengerApp.repository.ConversationRepository;
import com.example.MessengerApp.repository.MessageRepository;
import com.example.MessengerApp.repository.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    ConversationMapper mapper = Mappers.getMapper(ConversationMapper.class);

    public ConversationServiceImpl(ConversationRepository conversationRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    @Override
    public List<ConversationDTO> getAllConversations() {
        List<Conversation> conversations = conversationRepository.findAll();
        List<ConversationDTO> conversationDTOs = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationDTOs.add(mapper.toDTO(conversation));
        }
        return conversationDTOs;
    }

    @Override
    public ConversationDTO getConversationById(Long id) {
        Conversation conversation = conversationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Conversation not found with id: " + id));
        return mapper.toDTO(conversation);
    }


    @Override
    public void saveConversation(ConversationDTO conversation) {
        List<User> users = conversation.getUsers();
        System.out.println("Conversation ID is "+ conversation.getId());
        for (User user : users) {
            if (!userRepository.findById(user.getId()).isPresent()) {
                throw new EntityNotFoundException("User with ID " + user.getId() + " not found");
            }
        }
        Conversation newConv = new Conversation(conversation.getId(), conversation.getUsers(),conversation.getDateStarted());
        newConv.setDateStarted(new Date());
        conversationRepository.save(newConv);
    }

    @Override
    public void deleteConversation(ConversationDTO conversation) {
        Conversation conv = conversationRepository.findById(conversation.getId()).orElseThrow(() -> new EntityNotFoundException("Conversation not found with id: " + conversation.getId()));
        List<Message> messages = messageRepository.findAll().stream().filter(msg->msg.getConversation().equals(conv)).collect(Collectors.toList());
        messageRepository.deleteAll(messages);
        conversationRepository.delete(conv);
    }

    @Override
    public ConversationDTO updateConversation(ConversationDTO conversation) {
        Conversation existingConversation = conversationRepository.findById(conversation.getId())
                .orElseThrow(() -> new EntityNotFoundException("Conversation not found with id: " + conversation.getId()));

        List<User> users = conversation.getUsers();
        for (User user : users) {
            if (!userRepository.findById(user.getId()).isPresent()) {
                throw new EntityNotFoundException("User with ID " + user.getId() + " not found");
            }
        }

        if (conversation.getUsers() != null)
            existingConversation.setUsers(conversation.getUsers());
        conversationRepository.save(existingConversation);

        return mapper.toDTO(existingConversation);
    }

    @Override
    public List<ConversationDTO> getAllConversationByUserId(Long id) {
        List<Conversation> conversations = conversationRepository.findAll();
        List<ConversationDTO> conversationDTOs = new ArrayList<>();
        User user = userRepository.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id)));

        for (Conversation conversation : conversations) {
            conversationDTOs.add(mapper.toDTO(conversation));
        }
        return conversationDTOs.stream().filter(conv -> conv.getUsers().contains(user)).collect(Collectors.toList());
    }
}
