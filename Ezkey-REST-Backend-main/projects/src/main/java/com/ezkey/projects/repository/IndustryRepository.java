package com.ezkey.projects.repository;

import com.ezkey.projects.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustryRepository extends JpaRepository<Industry,Long> {
    List<Industry> findByIndustryNameContainingIgnoreCase(String input);
}
