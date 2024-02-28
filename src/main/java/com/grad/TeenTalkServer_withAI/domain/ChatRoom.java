package com.grad.TeenTalkServer_withAI.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "chatroom_table")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "room_id")
    private Long id;

    @Column(name = "user1")
    private Long user1;

    @Column(name = "user2")
    private Long user2;


}
