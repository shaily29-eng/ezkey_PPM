package com.ezkey.userservice.repository;

import com.ezkey.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByWorkerLoginId(String workerLoginId);

    Optional<List<User>> findUsersByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(String query, String query2);
}
