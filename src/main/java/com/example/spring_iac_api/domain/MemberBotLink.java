package com.example.spring_iac_api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "IAC_MEMBER_BOT_LINK")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberBotLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_BOT_LINK_SEQ")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @Column(name = "BOT_ID")
    private String botId;

    @Column(name = "BOT_USER_ID")
    private String botUserId;

}
