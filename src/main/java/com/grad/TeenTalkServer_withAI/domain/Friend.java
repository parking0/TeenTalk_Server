package com.grad.TeenTalkServer_withAI.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "friend_table")
public class Friend {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="fromUser")
    private Member fromUser;                    // 당사자

    @ManyToOne
    @JoinColumn(name="toUser")                  // 팔로우 목록
    private Member toUser;

    //private boolean eachother;            // 맞팔

}
