package com.example.MessengerApp.controllers;

import com.example.MessengerApp.dto.MessageDTO;
import com.example.MessengerApp.service.MessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiOperation(value = "Returns a list of all  messages ")
    @GetMapping("/")
    public List<MessageDTO> getAllMessages() {
        return messageService.getAllMessages();
    }

    @ApiOperation(value = "Returns a message based on  ID")
    @GetMapping("/{id}")
    public MessageDTO getMessageById(@ApiParam("Id of the message you want to retrieve") @PathVariable Long id) {
        return messageService.getMessageById(id);
    }
    @ApiOperation(value = "Returns a list of messages  based on  conversation ID")
    @GetMapping("/conversation/{conversationId}")
    public List<MessageDTO> getAllMessagesByConversationId(@ApiParam("Id of the conversation you want to view messages from")@PathVariable Long conversationId) {
        return messageService.getAllMessagesByConversationId(conversationId);
    }

    @ApiOperation(value = "Sends a message from an user to another")
    @PostMapping("/sendfrom/{fromId}/to/{toId}")
    public ResponseEntity<String> sendMessageToUser(@ApiParam("Id of user sending the message")@PathVariable Long fromId, @ApiParam("Id of user receiving the message")@PathVariable Long toId, @ApiParam("Body of the message,containing a string only")@RequestBody MessageDTO messageDTO) {
        try {

            messageService.sendMessageToUser(messageDTO, fromId, toId);
            return ResponseEntity.ok("Message sent successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message data");
        }
    }

    @ApiOperation(value = "Sends a message from an user in a conversation")
    @PostMapping("/sendfrom/{fromId}/in/{conversationId}")
    public ResponseEntity<String> sendMessage(@ApiParam("Id of user sending the message")@PathVariable Long fromId,@ApiParam("Id of the conversation ") @PathVariable Long conversationId,@ApiParam("Body of the message,containing a string only") @RequestBody MessageDTO messageDTO) {
        try {

            messageService.sendMessage(messageDTO, fromId, conversationId);
            return ResponseEntity.ok("Message sent successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message data");
        }
    }
    @ApiOperation(value = "Updates a message ")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMessage(@ApiParam("Body of the message")@RequestBody MessageDTO messageDTO, @ApiParam("Id of the message you want to modify ")@PathVariable Long id) {
        try {
            messageDTO.setId(id);
            messageService.updateMessage(messageDTO);
            return ResponseEntity.ok("Message updated successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message data");
        }
    }

    @ApiOperation(value = "Deletes a message ")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@ApiParam("Id of the message you want to delete ")@PathVariable Long id) {
        try {
            MessageDTO messageDTO = messageService.getMessageById(id);
            messageService.deleteMessage(messageDTO);
            return ResponseEntity.ok("Message deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message ID");
        }
    }
}
