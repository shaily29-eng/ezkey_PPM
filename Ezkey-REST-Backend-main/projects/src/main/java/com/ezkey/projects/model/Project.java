package com.ezkey.projects.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Projects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectId")
    private Integer projectId;

    // Other columns
    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    @Column(name = "CreatedOn")
    private Date createdOn;

    @Column(name = "ModifiedOn")
    private Date modifiedOn;

    @Column(name = "SquareFootage")
    private Integer squareFootage;

    @Column(name = "Headcount")
    private Integer headCount;

    @Column(name = "ProjectAddress")
    private String projectAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ClientId")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IndustryId")
    private Industry industry;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LocationId")
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BillingTypeId")
    private BillingType billingType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BusinessUnitId")
    private BusinessUnit businessUnit;

    @Column(name = "ProjectManager")
    private Integer projectManagerId;
}