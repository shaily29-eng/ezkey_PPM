package com.ezkey.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequestDto {
    private String workLoginId;
    private String firstName;
    private String lastName;
    private String locationId;
    private String hireDate;
    private String terminationDate;
    private int businessUnit;
    private int jobTitleId;
    private int ManagerId;
}
