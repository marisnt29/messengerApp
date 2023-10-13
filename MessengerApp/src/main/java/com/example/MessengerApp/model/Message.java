package com.example.MessengerApp.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.util.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
    @NotNull
    private User from;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conversation_id")
    @NotNull
    private Conversation conversation;

    @NotNull
    private String content;

    @NotNull
    private Date dateSent;
    private Date dateDelivered;
    private Date dateViewed;

    public static class MessageBuilder {
        public MessageBuilder() {

            this.dateSent = new Date();
            this.dateDelivered = new Date();
            this.dateViewed = null;
        }

        public MessageBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MessageBuilder from(User from) {
            this.from = from;
            return this;
        }

        public MessageBuilder conversation(Conversation conversation) {
            this.conversation = conversation;
            return this;
        }

        public MessageBuilder content(String content) {
            this.content = content;
            return this;
        }

        public MessageBuilder dateSent(Date dateSent) {
            this.dateSent = dateSent;
            return this;
        }

        public MessageBuilder dateDelivered(Date dateDelivered) {
            this.dateDelivered = dateDelivered;
            return this;
        }

        public MessageBuilder dateViewed(Date dateViewed) {
            this.dateViewed = dateViewed;
            return this;
        }

        public Message build() {
            return new Message(id, from, conversation, content, dateSent, dateDelivered, dateViewed);
        }
    }


}
