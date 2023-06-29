package com.example.spring_iac_api.repository;

import com.example.spring_iac_api.domain.MemberMembershipLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMembershipLinkRepository extends JpaRepository<MemberMembershipLink,Long> {
}
