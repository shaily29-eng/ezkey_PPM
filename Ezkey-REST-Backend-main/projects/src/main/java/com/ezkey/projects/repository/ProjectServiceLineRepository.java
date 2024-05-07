package com.ezkey.projects.repository;

import com.ezkey.projects.model.ProjectServiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectServiceLineRepository extends JpaRepository<ProjectServiceLine, Integer> {

//    Optional<List<ProjectServiceLine>> findProjectServiceLinesByProjectId(Integer projectId);

    Optional<List<ProjectServiceLine>> findProjectServiceLinesByProjectProjectId(Integer projectId);
    Optional<List<ProjectServiceLine>> findProjectServiceLinesByProjectProjectIdIn(List<Integer> ids);
}
