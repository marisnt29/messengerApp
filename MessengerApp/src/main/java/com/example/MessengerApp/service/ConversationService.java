package com.example.MessengerApp.service;

import com.example.MessengerApp.dto.ConversationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ConversationService {
    List<ConversationDTO> getAllConversations();
    ConversationDTO getConversationById(Long id);
    void saveConversation(ConversationDTO conversation);
    void deleteConversation(ConversationDTO conversation);
    ConversationDTO updateConversation(ConversationDTO conversation);
    List<ConversationDTO> getAllConversationByUserId(Long id);
}
