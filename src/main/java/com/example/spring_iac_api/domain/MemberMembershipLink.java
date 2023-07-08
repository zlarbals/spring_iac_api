package com.example.spring_iac_api.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "IAC_MEMBER_MEMBERSHIP_LINK")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMembershipLink extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_MEMBERSHIP_LINK_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBERSHIP_NAME")
    private Membership membership;

}
