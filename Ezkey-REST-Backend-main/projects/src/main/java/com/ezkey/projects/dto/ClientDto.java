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
public class ClientDto implements Serializable {
    private Integer clientId;
    private String clientName;
    private String locationId;
    private String createdOn;
}