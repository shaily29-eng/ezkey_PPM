package com.ezkey.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JobTitleDto {
    private Integer jobTitleId;
    private String jobTitle;
}
