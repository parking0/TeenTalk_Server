package com.grad.TeenTalkServer_withAI.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter             // 나중에 지우기
@Getter
@NoArgsConstructor
@Table(name = "message_table")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "message_id")
    private Long id;

    @Column(name = "content")
    private String content;     //메시지 내용

    @Column(name = "room_id")
    private String roomId;      //채팅방 아이디

    @Column(name = "sender")
    private String sender;      //보내는 사람

    @Column(name = "receiver")
    private String receiver;     //받는 사람               - 1:1이기 때문에 안 필요할듯

    @Column(name = "create_at")
    private String create_at;      //메시지 시간

    @Column(name = "msg_type")
    @Enumerated(EnumType.STRING)
    private MessageType msgType;

}

// localhost:8080/message/send?messageId=1234&content=aa&roomId=a&sender=a&receiver=a&create_at=a&msgType=TALK
