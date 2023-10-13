package com.example.MessengerApp.service;

import com.example.MessengerApp.dto.MessageDTO;
import com.example.MessengerApp.mappers.MessageMapper;
import com.example.MessengerApp.model.Conversation;
import com.example.MessengerApp.model.Message;
import com.example.MessengerApp.model.User;
import com.example.MessengerApp.repository.ConversationRepository;
import com.example.MessengerApp.repository.MessageRepository;
import com.example.MessengerApp.repository.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    MessageMapper mapper = Mappers.getMapper(MessageMapper.class);

    public MessageServiceImpl(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<MessageDTO> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        List<MessageDTO> messageDTOS = new ArrayList<>();
        for (Message message : messages) {
            messageDTOS.add(mapper.toDTO(message));
        }
        return messageDTOS;
    }

    @Override
    public MessageDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id).orElse(null);
        return mapper.toDTO(message);
    }

    @Override
    public MessageDTO sendMessage(MessageDTO messageDTO, Long fromId, Long conversationId) throws IllegalArgumentException {


        User user = userRepository.findById(fromId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + fromId));
        Conversation conversation = conversationRepository.findById(conversationId) .orElseThrow(() -> new EntityNotFoundException("Conversation not found with id: " + conversationId));
        if(!conversation.getUsers().contains(user)){
            throw new IllegalArgumentException("User is not part of this conversation");
        }

        Message message = Message.builder()
                .id(messageDTO.getId())
                .from(user)
                .conversation(conversation)
                .content(messageDTO.getContent())
                .build();

        messageRepository.save(message);
        return mapper.toDTO(message);
    }

    @Override
    public void sendMessageToUser(MessageDTO messageDTO, Long fromId, Long toId) {

        User user1 = userRepository.findById(fromId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + fromId));
        User user2 = userRepository.findById(toId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + toId));
        List<Conversation> conversations;
         conversations = conversationRepository.findAll().stream().filter(conv->conv.getUsers().contains(user1)&&conv.getUsers().contains(user2)&&conv.getUsers().size()==2).limit(1).collect(Collectors.toList());
        Conversation conversation;
        if(conversations.size()<1) {
            conversation = Conversation.between(Arrays.asList(user1, user2));
            conversationRepository.save(conversation);
        }
         else {
            conversation = conversations.get(0);
        }

         if (messageDTO.getContent()!= null){
             Message message = Message.builder()
                     .id(messageDTO.getId())
                     .from(user1)
                     .conversation(conversation)
                     .content(messageDTO.getContent())
                     .build();

             messageRepository.save(message);
         }


    }

    @Override
    public void updateMessage(MessageDTO messageDTO) {
        Message message = messageRepository.findById(messageDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + messageDTO.getId()));

        if(messageDTO.getContent()!=null)
        message.setContent(messageDTO.getContent());

        message.setDateViewed(new Date());
        messageRepository.save(message);
    }

    @Override
    public void deleteMessage(MessageDTO messageDTO) {
        Message message = messageRepository.findById(messageDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + messageDTO.getId()));
        messageRepository.delete(message);
    }

    @Override
    public List<MessageDTO> getAllMessagesByConversationId(Long conversationId) {
        List<MessageDTO> messageDTOs = new ArrayList<>();
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new EntityNotFoundException("Conversation not found with id: " + conversationId));
        List<Message> messages = messageRepository.findAll().stream().filter(msg -> msg.getConversation().equals(conversation)).collect(Collectors.toList());

        for (Message message : messages) {
            messageDTOs.add(mapper.toDTO(message));
        }
        return messageDTOs;
    }
}
