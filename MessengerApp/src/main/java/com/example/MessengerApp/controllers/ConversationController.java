package com.example.MessengerApp.controllers;

import com.example.MessengerApp.dto.ConversationDTO;
import com.example.MessengerApp.service.ConversationServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationServiceImpl conversationService;

    public ConversationController(ConversationServiceImpl conversationService) {
        this.conversationService = conversationService;
    }

    @ApiOperation(value = "Return a list of all conversations ")
    @GetMapping("/")
    public List<ConversationDTO> getAllConversations() {
        return conversationService.getAllConversations();
    }

        @ApiOperation(value = "Return a list of all conversations from an user ")
        @GetMapping("/user/{id}")
        public List<ConversationDTO> getAllConversationsByUserId(@ApiParam("id of the user") @PathVariable Long id) {
            return conversationService.getAllConversationByUserId(id);
        }
    @ApiOperation(value = "Return a  conversation based on id ")
    @GetMapping("/{id}")
    public ConversationDTO getConversationById(@ApiParam("id of the conversation")@PathVariable Long id) {

        return conversationService.getConversationById(id);
    }
    @ApiOperation(value = " Creates an conversation and saves it in database ")
    @PostMapping("/save")
    public void saveConversation(@ApiParam("body of the conversation")@RequestBody ConversationDTO conversationDto) {
           conversationService.saveConversation(conversationDto);
    }
    @ApiOperation(value = " Updates an conversation ")
    @PutMapping("/{id}")
    public void updateConversation(@ApiParam("body of the conversation")@RequestBody ConversationDTO conversationDto,@ApiParam("id of conversation you want to update")@PathVariable Long id) {
        conversationDto.setId(id);

        conversationService.updateConversation(conversationDto);
    }
    @ApiOperation(value = " Deletes an conversation ")
    @DeleteMapping("/{id}")
    public void deleteConversation(@ApiParam("id of the conversation")@PathVariable Long id) {
        ConversationDTO conversation = conversationService.getConversationById(id);

        conversationService.deleteConversation(conversation);
    }


}

