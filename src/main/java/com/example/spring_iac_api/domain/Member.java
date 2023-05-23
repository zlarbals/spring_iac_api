package com.example.spring_iac_api.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "IAC_MEMBER")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_SEQ")
    private Long seq;

    @Column(name = "PASSWORD")
    private String password;

    @Column(unique = true, name = "EMAIL")
    private String email;

    @Column(name = "USE_YN")
    @Enumerated(EnumType.STRING)
    private UseStatus useYn;

    @Column(name = "DISABLED_DATE")
    private LocalDate disabledDate;

}
