package com.example.MessengerApp.mappers;

import com.example.MessengerApp.dto.MessageDTO;
import com.example.MessengerApp.model.Message;
import com.example.MessengerApp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface MessageMapper {


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "dateSent",target = "dateSent"),
            @Mapping(source = "dateDelivered",target = "dateDelivered"),
            @Mapping(source = "dateViewed",target = "dateViewed")
    })
    MessageDTO toDTO(Message message);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "content", target = "content"),
            @Mapping(target = "from", ignore = true),
            @Mapping(source = "dateSent",target = "dateSent"),
            @Mapping(source = "dateDelivered",target = "dateDelivered"),
            @Mapping(source = "dateViewed",target = "dateViewed")
    })
    Message toEntity(MessageDTO messageDTO);

    default String map(User value){
        return value.getUsername();
    }
}
