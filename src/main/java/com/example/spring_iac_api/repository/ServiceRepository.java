package com.example.spring_iac_api.repository;

import com.example.spring_iac_api.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, String> {

    Optional<Service> findServiceByKey(String serviceKey);

}
