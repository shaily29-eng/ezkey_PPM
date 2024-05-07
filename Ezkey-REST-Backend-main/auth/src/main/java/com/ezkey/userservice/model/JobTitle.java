package com.ezkey.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="JobTitles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JobTitleId")
    private int jobTitleId;
    @Column(name="JobTitle")
    private String jobTitle;
    @Column(name="CreatedOn")
    private Date createdOn;
    @Column(name="ModifiedOn")
    private Date modifiedOn;
}
