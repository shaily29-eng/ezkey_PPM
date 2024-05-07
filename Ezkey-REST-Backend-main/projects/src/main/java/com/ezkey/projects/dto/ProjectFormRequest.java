package com.ezkey.projects.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectFormRequest {
    private Integer clientId;
    private String projectName;
    private String startDate;
    private String endDate;
    private String createdOn;
    private String modifiedOn;
    private Integer squareFootage;
    private Integer headCount;
    private Integer industryId;
    private String projectAddress;
    private String locationId;
    private Integer billingTypeId;
    private Integer businessUnitId;
    private Integer projectManagerId;
    private List<Integer> serviceLineIds;
}
