package com.motta.insurance_scheme_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motta.insurance_scheme_service.entity.Scheme;

public interface SchemeRepository extends JpaRepository<Scheme, Integer> {

}
