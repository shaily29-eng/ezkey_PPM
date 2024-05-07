package com.ezkey.projects.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceLineDto implements Serializable {
    private Integer serviceLineId;
    private String serviceLineName;
    private String createdOn;
}