package com.example.MessengerApp.mappers;

import com.example.MessengerApp.dto.MessageDTO;
import com.example.MessengerApp.model.Message;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-13T03:16:28+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.17 (Oracle Corporation)"
)
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDTO toDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setId( message.getId() );
        messageDTO.setContent( message.getContent() );
        messageDTO.setDateSent( message.getDateSent() );
        messageDTO.setDateDelivered( message.getDateDelivered() );
        messageDTO.setDateViewed( message.getDateViewed() );
        messageDTO.setFrom( map( message.getFrom() ) );

        return messageDTO;
    }

    @Override
    public Message toEntity(MessageDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        Message.MessageBuilder message = Message.builder();

        message.id( messageDTO.getId() );
        message.content( messageDTO.getContent() );
        message.dateSent( messageDTO.getDateSent() );
        message.dateDelivered( messageDTO.getDateDelivered() );
        message.dateViewed( messageDTO.getDateViewed() );

        return message.build();
    }
}
