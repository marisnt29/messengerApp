package com.example.MessengerApp.mappers;

import com.example.MessengerApp.dto.ConversationDTO;
import com.example.MessengerApp.model.Conversation;
import com.example.MessengerApp.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-13T03:16:28+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.17 (Oracle Corporation)"
)
public class ConversationMapperImpl implements ConversationMapper {

    @Override
    public ConversationDTO toDTO(Conversation conversation) {
        if ( conversation == null ) {
            return null;
        }

        ConversationDTO conversationDTO = new ConversationDTO();

        conversationDTO.setId( conversation.getId() );
        List<User> list = conversation.getUsers();
        if ( list != null ) {
            conversationDTO.setUsers( new ArrayList<User>( list ) );
        }
        conversationDTO.setDateStarted( conversation.getDateStarted() );

        return conversationDTO;
    }

    @Override
    public Conversation toEntity(ConversationDTO conversationDTO) {
        if ( conversationDTO == null ) {
            return null;
        }

        Conversation conversation = new Conversation();

        conversation.setId( conversationDTO.getId() );
        List<User> list = conversationDTO.getUsers();
        if ( list != null ) {
            conversation.setUsers( new ArrayList<User>( list ) );
        }
        conversation.setDateStarted( conversationDTO.getDateStarted() );

        return conversation;
    }
}
