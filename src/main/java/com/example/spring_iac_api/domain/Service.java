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
@Table(name = "IAC_SERVICE")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Service extends BaseTimeEntity{

    @Id
    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "SERVICE_KEY", unique = true)
    private String key;
}
