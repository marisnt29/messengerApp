package com.example.MessengerApp.mappers;

import com.example.MessengerApp.dto.ConversationDTO;
import com.example.MessengerApp.model.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ConversationMapper {


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "users", target = "users"),
            @Mapping(source = "dateStarted", target = "dateStarted")
    })
    ConversationDTO toDTO(Conversation conversation);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "users", target = "users"),
            @Mapping(source = "dateStarted", target = "dateStarted")
    })
    Conversation toEntity(ConversationDTO conversationDTO);


}
