package com.example.MessengerApp.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
public class Conversation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
    private List<User> users;



    private Date dateStarted;


    public static Conversation between(List<User> users) {
        if (users == null || users.size() < 2) {
            throw new IllegalArgumentException("There should be at least 2 users to the conversation");
        }
        Conversation conversation = new Conversation();
        conversation.setUsers(users);
        conversation.setDateStarted(new Date());
        return conversation;
    }


}
