package com.example.spring_iac_api.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IAC_MEMBERSHIP")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership extends BaseTimeEntity{

    @Id
    @Column(name = "MEMBERSHIP_NAME")
    private String membershipName;

    @Column(name = "MEMBERSHIP_KEY", unique = true)
    private String key;
}
