package com.ezkey.projects.controller;

import com.ezkey.projects.dto.*;
import com.ezkey.projects.service.ProjectsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;

@RestController
@SecurityRequirement(name = "keycloakAuth")
@RequestMapping("/api/projects/")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.HEAD})
public class ProjectsController {
    private final ProjectsService projectsService;
    private final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

    @Autowired
    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @PostMapping("/add")
    //Is there a better place to move exception? Need better logging
    public Mono<ResponseEntity<GenericResponse<String>>> addProject(@RequestBody ProjectFormRequest projectFormRequest) {
        try {
            projectsService.saveProject(projectFormRequest);
            return Mono.just(ResponseEntity.ok(GenericResponse.success("Project added successfully")));
        } catch (Exception e) {
            if (e instanceof ParseException) {
                return Mono.just(ResponseEntity.badRequest().body(GenericResponse.error("Check date format", 400)));
            } else {
                return Mono.just(ResponseEntity.internalServerError().build());
            }
        }
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/billingtypes/suggestions")
    public Mono<ResponseEntity<GenericResponse<List<BillingTypeDto>>>> getBillingTypeSuggestions(@RequestParam(name = "query") String input) {
        return Mono.just(ResponseEntity.ok(GenericResponse.success(projectsService.getBillingTypeSuggestions(input))));
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/clients/suggestions")
    public Mono<ResponseEntity<GenericResponse<List<ClientDto>>>> getClientSuggestions(@RequestParam(name = "query") String input) {
        return Mono.just(ResponseEntity.ok(GenericResponse.success(projectsService.getClientSuggestions(input))));
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/industries/suggestions")
    public Mono<ResponseEntity<GenericResponse<List<IndustryDto>>>> getIndustrySuggestions(@RequestParam(name = "query") String input) {
        return Mono.just(ResponseEntity.ok(GenericResponse.success(projectsService.getIndustrySuggestions(input))));
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/servicelines/suggestions")
    public Mono<ResponseEntity<GenericResponse<List<ServiceLineDto>>>> getServiceLineSuggestions(@RequestParam(name = "query") String input) {
        return Mono.just(ResponseEntity.ok(GenericResponse.success(projectsService.getServiceLineSuggestions(input))));
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/list/id")
    public Mono<ResponseEntity<GenericResponse<List<ProjectResponseDto>>>> getProjectsForWorker(Authentication auth) {
        try {
            JwtAuthenticationToken token = (JwtAuthenticationToken) auth;
            Map<String, Object> claims = token.getTokenAttributes();
            logger.atDebug().log(String.valueOf(claims.get("sub")));
            return Mono.just(ResponseEntity.ok(GenericResponse.success(projectsService.getDashboardProjectList(String.valueOf(claims.get("sub"))))));
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.just(ResponseEntity.internalServerError().build());
        }
    }
}
