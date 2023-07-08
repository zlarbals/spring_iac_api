package com.example.spring_iac_api.repository;

import com.example.spring_iac_api.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsMemberByEmail(String email);

    Optional<Member> findMemberByEmail(String email);

    @Query("SELECT m FROM Member m INNER JOIN m.membershipList s WHERE s.membership.membershipName = :serviceName")
    @EntityGraph(attributePaths = "membershipList", type = EntityGraph.EntityGraphType.LOAD)
    List<Member> findMembersByMembershipList(@Param("serviceName") String serviceName);

}
