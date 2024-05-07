package com.ezkey.userservice.repository;

import com.ezkey.userservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<List<Location>> findLocationsByNameContainsIgnoreCaseOrMunicipalityContainsIgnoreCase(String query1, String query2);
}
