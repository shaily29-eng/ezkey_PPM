package com.ezkey.userservice.repository;

import com.ezkey.userservice.model.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Integer> {
    Optional<List<JobTitle>> findJobTitlesByJobTitleContainsIgnoreCase(String query);
}
