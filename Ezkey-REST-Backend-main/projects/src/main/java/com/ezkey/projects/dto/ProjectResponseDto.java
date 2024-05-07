package com.ezkey.projects.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponseDto {
    private Integer projectId;
    private IdValuePair client;
    private String projectName;
    private String startDate;
    private String endDate;
    private String createdOn;
    private String modifiedOn;
    private Integer squareFootage;
    private Integer headCount;
    private IdValuePair industry;
    private String projectAddress;
    private Location location;
    private IdValuePair billingType;
    private IdValuePair businessUnit;
    private IdValuePair projectManager;
    private List<IdValuePair> serviceLines;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class IdValuePair {
        private Integer id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Location {
        private String id;
        private String name;
        private String latitude;
        private String longitude;
    }
}
