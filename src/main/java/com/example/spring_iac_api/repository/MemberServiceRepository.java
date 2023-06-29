package com.example.spring_iac_api.repository;

import com.example.spring_iac_api.domain.MemberService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberServiceRepository extends JpaRepository<MemberService,Long> {
}
