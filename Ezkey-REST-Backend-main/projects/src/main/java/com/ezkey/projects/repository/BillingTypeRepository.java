package com.ezkey.projects.repository;

import com.ezkey.projects.model.BillingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingTypeRepository extends JpaRepository<BillingType,Long> {
    List<BillingType> findByBillingTypeNameContainingIgnoreCase(String input);
}
