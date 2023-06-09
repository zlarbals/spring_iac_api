package com.example.spring_iac_api.repository;

import com.example.spring_iac_api.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, String> {

    Optional<Membership> findMembershipByKey(String membershipKey);

}
