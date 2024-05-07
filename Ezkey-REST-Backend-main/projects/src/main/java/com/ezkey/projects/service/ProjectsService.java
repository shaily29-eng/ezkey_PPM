package com.ezkey.projects.service;

import com.ezkey.projects.config.RabbitConfig;
import com.ezkey.projects.dto.*;
import com.ezkey.projects.event.UserIdRequestEvent;
import com.ezkey.projects.event.UserIdResponseEvent;
import com.ezkey.projects.model.*;
import com.ezkey.projects.repository.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ProjectsService {


    private final BillingTypeRepository billingTypeRepository;
    private final ClientRepository clientRepository;
    private final IndustryRepository industryRepository;
    private final ServiceLineRepository serviceLineRepository;
    private final ProjectRepository projectRepository;

    private final ProjectServiceLineRepository projectServiceLineRepository;


    private final RabbitTemplate rabbitTemplate;

    private final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";


    @Autowired
    public ProjectsService(
            BillingTypeRepository billingTypeRepository,
            ClientRepository clientRepository,
            IndustryRepository industryRepository,
            ServiceLineRepository serviceLineRepository,
            ProjectRepository projectRepository,
            ProjectServiceLineRepository projectServiceLineRepository,
            RabbitTemplate rabbitTemplate

    ) {
        this.billingTypeRepository = billingTypeRepository;
        this.clientRepository = clientRepository;
        this.industryRepository = industryRepository;
        this.serviceLineRepository = serviceLineRepository;
        this.projectRepository = projectRepository;
        this.projectServiceLineRepository = projectServiceLineRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<BillingTypeDto> getBillingTypeSuggestions(String input) {
        List<BillingType> billingTypes = billingTypeRepository.findByBillingTypeNameContainingIgnoreCase(input);
        return billingTypes.stream()
                .map(this::mapToBillingTypeResponse)
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<ClientDto> getClientSuggestions(String input) {
        List<Client> clients = clientRepository.findByClientNameContainingIgnoreCase(input);
        return clients.stream()
                .map(this::mapToClientResponse)
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<IndustryDto> getIndustrySuggestions(String input) {
        List<Industry> industries = industryRepository.findByIndustryNameContainingIgnoreCase(input);
        return industries.stream()
                .map(this::mapToIndustryResponse)
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<ServiceLineDto> getServiceLineSuggestions(String input) {
        List<ServiceLine> serviceLines = serviceLineRepository.findByServiceLineNameContainingIgnoreCase(input);
        return serviceLines.stream()
                .map(this::mapToServiceLineResponse)
                .limit(5)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveProject(ProjectFormRequest projectFormRequest) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date createdOn = projectFormRequest.getCreatedOn() != null ? formatter.parse(projectFormRequest.getCreatedOn()) : null;
        Date modifiedOn = projectFormRequest.getModifiedOn() != null ? formatter.parse(projectFormRequest.getModifiedOn()) : null;
        Date startDate = projectFormRequest.getStartDate() != null ? formatter.parse(projectFormRequest.getStartDate()) : null;
        Date endDate = projectFormRequest.getEndDate() != null ? formatter.parse(projectFormRequest.getEndDate()) : null;
        Project project = Project.builder()
                .client(Client.builder().clientId(projectFormRequest.getClientId()).build())
                .projectAddress(projectFormRequest.getProjectAddress())
                .projectName(projectFormRequest.getProjectName())
                .location(Location.builder().locationId(projectFormRequest.getLocationId()).build())
                .createdOn(createdOn)
                .modifiedOn(modifiedOn)
                .startDate(startDate)
                .endDate(endDate)
                .businessUnit(BusinessUnit.builder().businessUnitId(projectFormRequest.getBusinessUnitId()).build())
                .industry(Industry.builder().industryId(projectFormRequest.getIndustryId()).build())
                .headCount(projectFormRequest.getHeadCount())
                .billingType(BillingType.builder().billingTypeId(projectFormRequest.getBillingTypeId()).build())
                .squareFootage(projectFormRequest.getSquareFootage())
                .projectManagerId(projectFormRequest.getProjectManagerId())
                .build();
        Project savedProject = projectRepository.save(project);
        List<ServiceLine> serviceLines = serviceLineRepository.findAllByServiceLineIdIn(projectFormRequest.getServiceLineIds());
        for (ServiceLine serviceLine : serviceLines) {
            projectServiceLineRepository.save(ProjectServiceLine.builder()
                    .project(savedProject)
                    .serviceLine(serviceLine)
                    .build());
        }
    }

    @Transactional
    public List<ProjectResponseDto> getDashboardProjectList(String userLoginId) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Logger.getGlobal().log(Level.ALL, "Sending user request" + userLoginId);

        // Do rpc call to another microservice to get userId
        UserIdResponseEvent response = rabbitTemplate.convertSendAndReceiveAsType(RabbitConfig.USER_ID_EXCHANGE, RabbitConfig.USER_ID_REQUEST_TOPIC, UserIdRequestEvent.builder().userLoginId(userLoginId).build(), new ParameterizedTypeReference<UserIdResponseEvent>() {
        });
        // Fetch all projects based on userId
        List<Project> projects = projectRepository.findProjectsByProjectManagerId(response.getUserId()).orElse(new ArrayList<>());
        List<Integer> projectIds = projects.stream().map(Project::getProjectId).toList();
        // Fetch all serviceLines for a project
        List<ProjectResponseDto.IdValuePair> svcLines = projectServiceLineRepository.findProjectServiceLinesByProjectProjectIdIn(projectIds)
                .orElse(new ArrayList<>())
                .stream()
                .map(it -> ProjectResponseDto.IdValuePair
                        .builder()
                        .name(it.getServiceLine().getServiceLineName())
                        .id(it.getServiceLine().getServiceLineId())
                        .build())
                .toList();
        // map to a response object and return
        return projects.stream().map(it ->
                        ProjectResponseDto.builder()
                                .billingType(ProjectResponseDto.IdValuePair
                                        .builder()
                                        .id(it.getBillingType().getBillingTypeId())
                                        .name(it.getBillingType().getBillingTypeName())
                                        .build())
                                .projectId(it.getProjectId())
                                .projectAddress(it.getProjectAddress())
                                .projectName(it.getProjectName())
                                .businessUnit(ProjectResponseDto.IdValuePair
                                        .builder()
                                        .id(it.getBusinessUnit().getBusinessUnitId())
                                        .name(it.getBusinessUnit().getBusinessUnitName())
                                        .build())
                                .client(ProjectResponseDto.IdValuePair
                                        .builder()
                                        .id(it.getClient().getClientId())
                                        .name(it.getClient().getClientName())
                                        .build())
                                .projectManager(ProjectResponseDto.IdValuePair
                                        .builder()
                                        .id(response.getUserId())
                                        .name(response.getUserFullName())
                                        .build())
                                .createdOn(it.getCreatedOn() != null ? formatter.format(it.getCreatedOn()) : null)
                                .endDate(it.getEndDate() != null ? formatter.format(it.getEndDate()) : null)
                                .industry(ProjectResponseDto.IdValuePair
                                        .builder()
                                        .id(it.getIndustry().getIndustryId())
                                        .name(it.getIndustry().getIndustryName())
                                        .build())
                                .headCount(it.getHeadCount())
                                .location(ProjectResponseDto.Location
                                        .builder()
                                        .id(it.getLocation().getLocationId())
                                        .name(it.getLocation().getName())
                                        .latitude(it.getLocation().getLatitude())
                                        .longitude(it.getLocation().getLongitude())
                                        .build())
                                .serviceLines(svcLines)
                                .squareFootage(it.getSquareFootage())
                                .startDate(it.getStartDate() != null ? formatter.format(it.getStartDate()) : null)
                                .modifiedOn(it.getModifiedOn() != null ? formatter.format(it.getModifiedOn()) : null)
                                .build())
                .toList();
    }

    private BillingTypeDto mapToBillingTypeResponse(BillingType billingType) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return BillingTypeDto.builder()
                .billingTypeId(billingType.getBillingTypeId())
                .billingTypeName(billingType.getBillingTypeName())
                .createdOn(billingType.getCreatedOn() != null ? formatter.format(billingType.getCreatedOn()) : null)
                .build();
    }

    private ClientDto mapToClientResponse(Client client) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return ClientDto.builder()
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .locationId(client.getLocationId())
                .createdOn(client.getCreatedOn() != null ? formatter.format(client.getCreatedOn()) : null)
                .build();
    }

    private IndustryDto mapToIndustryResponse(Industry industry) {
        return IndustryDto.builder()
                .industryId(industry.getIndustryId())
                .industryName(industry.getIndustryName())
                .build();
    }

    private ServiceLineDto mapToServiceLineResponse(ServiceLine serviceLine) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return ServiceLineDto.builder()
                .serviceLineId(serviceLine.getServiceLineId())
                .serviceLineName(serviceLine.getServiceLineName())
                .createdOn(serviceLine.getCreatedOn() != null ? formatter.format(serviceLine.getCreatedOn()) : null)
                .build();
    }
}
