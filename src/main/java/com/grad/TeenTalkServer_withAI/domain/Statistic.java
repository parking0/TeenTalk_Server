package com.grad.TeenTalkServer_withAI.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "statistic_table")
public class Statistic {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="user")
    private Member user;                    // 당사자

    @Column(name = "clean")
    private int clean;

    @Column(name = "hate")
    private int hate;

}
