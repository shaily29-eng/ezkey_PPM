package com.ezkey.userservice.controller;

import com.ezkey.userservice.dto.*;
import com.ezkey.userservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "keycloakAuth")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.HEAD})
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/jobs/suggestions")
    public Mono<ResponseEntity<GenericResponse<List<JobTitleDto>>>> getJobs(@RequestParam(name = "query") String query) {
        return Mono.just(ResponseEntity.ok(GenericResponse.success(
                service.getJobTitles(query)
        )));
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/businessunit/suggestions")
    public Mono<ResponseEntity<GenericResponse<List<BusinessUnitDto>>>> getBusinessUnits(@RequestParam(name = "query") String query) {
        return Mono.just(ResponseEntity.ok(GenericResponse.success(
                service.getBusinessUnits(query)
        )));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/addnew")
    public Mono<ResponseEntity<GenericResponse<String>>> createUser(@RequestBody UserRequestDto request) {
        try {
            service.createUser(request);
            return Mono.just(ResponseEntity.ok(GenericResponse.success("User added successfully")));
        } catch (Exception e) {
            if (e instanceof ParseException) {
                return Mono.just(ResponseEntity.badRequest().body(GenericResponse.error("Check date format", 400)));
            } else {
                return Mono.just(ResponseEntity.internalServerError().body(GenericResponse.error("Something went wrong", 500)));
            }
        }
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/location/suggestions")
    public Mono<ResponseEntity<GenericResponse<List<LocationDto>>>> getLocation(@RequestParam(name = "query") String query) {
        return Mono.just(ResponseEntity.ok(GenericResponse.success(
                service.getLocation(query)
        )));
    }

    @PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
    @GetMapping("/name/suggestions")
    public Mono<ResponseEntity<GenericResponse<List<UserResponseDto>>>> getUsers(@RequestParam(name = "query") String query) {
        return Mono.just(ResponseEntity.ok(GenericResponse.success(
                service.getUsersByName(query)
        )));
    }
}
