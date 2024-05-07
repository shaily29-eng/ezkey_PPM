package com.ezkey.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BusinessUnitDto {
    private int businessUnitId;
    private String businessUnitName;
    private String createdOn;
}
