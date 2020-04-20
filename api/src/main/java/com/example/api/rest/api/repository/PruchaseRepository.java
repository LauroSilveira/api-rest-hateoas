package com.example.api.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.rest.api.model.Purchase;

public interface PruchaseRepository extends JpaRepository<Purchase, Long>{

}
