package com.example.MessengerApp.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean online;






}
