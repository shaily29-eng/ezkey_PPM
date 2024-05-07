package com.ezkey.userservice.service;

import com.ezkey.userservice.config.RabbitConfig;
import com.ezkey.userservice.dto.*;
import com.ezkey.userservice.event.UserIdRequestEvent;
import com.ezkey.userservice.event.UserIdResponseEvent;
import com.ezkey.userservice.model.BusinessUnit;
import com.ezkey.userservice.model.JobTitle;
import com.ezkey.userservice.model.Location;
import com.ezkey.userservice.model.User;
import com.ezkey.userservice.repository.BusinessUnitRepository;
import com.ezkey.userservice.repository.JobTitleRepository;
import com.ezkey.userservice.repository.LocationRepository;
import com.ezkey.userservice.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobTitleRepository jobTitleRepository;

    private final BusinessUnitRepository businessUnitRepository;

    private final LocationRepository locationRepository;

    private final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserService(UserRepository userRepo,
                       JobTitleRepository jobRepo,
                       BusinessUnitRepository businessRepo,
                       LocationRepository locationRepo,
                       RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepo;
        this.jobTitleRepository = jobRepo;
        this.businessUnitRepository = businessRepo;
        this.locationRepository = locationRepo;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createUser(UserRequestDto user) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date hiredDate = user.getHireDate() != null ? formatter.parse(user.getHireDate()) : null;
        Date terminationDate = user.getHireDate() != null ? formatter.parse(user.getHireDate()) : null;
        User udto = User.builder()
                .workerLoginId(user.getWorkLoginId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .hireDate(hiredDate)
                .terminationDate(terminationDate)
                .locationId(user.getLocationId())
                .businessUnitId(user.getBusinessUnit())
                .jobTitleId(user.getJobTitleId())
                .managerId(user.getManagerId())
                .build();
        userRepository.save(udto);
    }

    public List<JobTitleDto> getJobTitles(String query) {
        List<JobTitle> jobs = jobTitleRepository.findJobTitlesByJobTitleContainsIgnoreCase(query).orElse(new ArrayList<>());
        return jobs.stream()
                .map(it -> JobTitleDto.builder()
                        .jobTitle(it.getJobTitle())
                        .jobTitleId(it.getJobTitleId())
                        .build())
                .collect(Collectors.toList());
    }

    public List<BusinessUnitDto> getBusinessUnits(String query) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        List<BusinessUnit> units = businessUnitRepository.findBusinessUnitsByBusinessUnitNameContainsIgnoreCase(query).orElse(new ArrayList<>());
        return units.stream()
                .map(it -> BusinessUnitDto.builder()
                        .businessUnitName(it.getBusinessUnitName())
                        .businessUnitId(it.getBusinessUnitId())
                        .createdOn(it.getCreatedOn() != null ? formatter.format(it.getCreatedOn()) : null)
                        .build())
                .toList();
    }

    public List<LocationDto> getLocation(String query) {
        List<Location> units = locationRepository.findLocationsByNameContainsIgnoreCaseOrMunicipalityContainsIgnoreCase(query, query).orElse(new ArrayList<>());
        return units.stream()
                .map(it -> LocationDto.builder()
                        .name(it.getName())
                        .locationId(it.getLocationId())
                        .country(it.getCountry())
                        .type(it.getType())
                        .elevation(it.getElevation())
                        .longitude(it.getLongitude())
                        .latitude(it.getLatitude())
                        .municipality(it.getMunicipality())
                        .region(it.getRegion())
                        .build())
                .toList();
    }

    public List<UserResponseDto> getUsersByName(String query) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        List<User> users = userRepository.findUsersByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(query, query).orElse(new ArrayList<>());
        return users.stream()
                .map(it -> UserResponseDto.builder()
                        .workerId(it.getWorkerId())
                        .businessUnit(it.getBusinessUnitId())
                        .ManagerId(it.getManagerId())
                        .lastName(it.getLastName())
                        .firstName(it.getFirstName())
                        .hireDate(it.getHireDate() != null ? formatter.format(it.getHireDate()) : null)
                        .terminationDate(it.getHireDate() != null ? formatter.format(it.getHireDate()) : null)
                        .locationId(it.getLocationId())
                        .build())
                .toList();
    }

    @RabbitListener(queues = RabbitConfig.USER_ID_REQUEST_TOPIC)
    public UserIdResponseEvent userIdListener(@Payload UserIdRequestEvent request) {
        Logger.getGlobal().log(Level.ALL, "Received user request" + request.getUserLoginId());
        User user = userRepository.findUserByWorkerLoginId(request.getUserLoginId()).orElse(null);
        UserIdResponseEvent response = user != null ? UserIdResponseEvent
                .builder()
                .userId(user.getWorkerId())
                .userFullName(user.getFirstName() + " " + user.getLastName())
                .build() : null;
        rabbitTemplate.convertAndSend(RabbitConfig.USER_ID_EXCHANGE,
                RabbitConfig.USER_ID_RESPONSE_TOPIC,
                response);
        Logger.getGlobal().log(Level.ALL, "Sending user response" + request.getUserLoginId());
        return response;
    }
}
