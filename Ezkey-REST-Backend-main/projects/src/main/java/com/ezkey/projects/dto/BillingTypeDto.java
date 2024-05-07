package com.ezkey.projects.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingTypeDto implements Serializable {
    private Integer billingTypeId;
    private String billingTypeName;
    private String createdOn;
}