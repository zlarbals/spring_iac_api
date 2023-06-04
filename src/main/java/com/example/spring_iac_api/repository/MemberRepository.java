package com.example.spring_iac_api.repository;

import com.example.spring_iac_api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsMemberByEmail(String email);

    Optional<Member> findMemberByEmail(String email);

}
