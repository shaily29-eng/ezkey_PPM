package com.ezkey.projects.repository;

import com.ezkey.projects.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findByClientNameContainingIgnoreCase(String input);
}
