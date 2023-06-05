package com.example.spring_iac_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class MemberRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;

    @Size(min = 8, max = 20)
    private String password;

}
