package com.ezkey.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LocationDto {
    private String locationId;
    private String type;
    private String name;
    private Integer elevation;
    private String country;
    private String region;
    private String municipality;
    private String latitude;
    private String longitude;
}
