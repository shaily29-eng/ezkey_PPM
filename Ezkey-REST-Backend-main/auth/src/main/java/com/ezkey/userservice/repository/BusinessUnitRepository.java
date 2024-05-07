package com.ezkey.userservice.repository;

import com.ezkey.userservice.model.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Integer> {
    Optional<List<BusinessUnit>> findBusinessUnitsByBusinessUnitNameContainsIgnoreCase(String query);
}
