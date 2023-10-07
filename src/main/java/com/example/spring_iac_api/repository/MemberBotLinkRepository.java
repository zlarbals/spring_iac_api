package com.example.spring_iac_api.repository;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.domain.MemberBotLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBotLinkRepository extends JpaRepository<MemberBotLink, Long> {

    boolean existsMemberBotLinkByMemberAndBotUserId(Member member, String botUserId);

}
