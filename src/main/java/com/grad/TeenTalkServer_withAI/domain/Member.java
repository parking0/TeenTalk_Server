package com.grad.TeenTalkServer_withAI.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "member_table")
public class Member { //table 역할

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment, 기본 키 생성을 데이터베이스에 위임
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true, length = 255)          // 중복회원 방지 (유니크 조건)
    private String userName;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Column(name = "friends")
    private List<String> friends = new ArrayList<>();

    @Column(name = "clean")
    private Long clean;

    @Column(name = "hate")
    private Long hate;


    public Member( String userName, String userPw, List<String> friends) {
        this.userName = userName;
        this.userPw = userPw;
        this.friends = friends;
    }

    public Member(String userName, String userPw){
        this.userName = userName;
        this.userPw = userPw;
        this.clean = 0L;
        this.hate = 0L;
    }

    public void updateName(String newName){
        this.userName=newName;
    }

    public void addFriend(String friend){
        this.getFriends().add(friend);
    }

    public void addMessageType(MessageType messageType){
        if(messageType.equals(MessageType.HATE))
            hate++;
        else if(messageType.equals(MessageType.CLEAN))
            clean++;
    }
}
