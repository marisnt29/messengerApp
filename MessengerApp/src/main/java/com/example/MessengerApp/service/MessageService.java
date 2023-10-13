package com.example.MessengerApp.service;

import com.example.MessengerApp.dto.MessageDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MessageService {

    List<MessageDTO> getAllMessages();

    MessageDTO getMessageById(Long id);

    MessageDTO sendMessage(MessageDTO messageDTO, Long fromId, Long conversationId) throws IllegalArgumentException ;
    void sendMessageToUser(MessageDTO messageDTO,Long fromId,Long toId);

    void updateMessage(MessageDTO messageDTO);

    void deleteMessage(MessageDTO messageDTO);

    List<MessageDTO> getAllMessagesByConversationId(Long conversationId);
}
