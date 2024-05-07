package com.ezkey.projects.repository;

import com.ezkey.projects.model.ServiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceLineRepository extends JpaRepository<ServiceLine, Long> {
    List<ServiceLine> findByServiceLineNameContainingIgnoreCase(String input);

    List<ServiceLine> findAllByServiceLineIdIn(List<Integer> ids);
}
