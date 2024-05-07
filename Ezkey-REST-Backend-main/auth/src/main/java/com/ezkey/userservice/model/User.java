package com.ezkey.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="Workers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkerId")
    private int workerId;
    @Column(name="WorkerLoginId", nullable = false)
    private String workerLoginId;
    @Column(name="FirstName")
    private String firstName;
    @Column(name="LastName")
    private String lastName;
    @Column(name="HireDate")
    private Date hireDate;
    @Column(name="TerminationDate")
    private Date terminationDate;
    @Column(name="LocationId")
    private String locationId;
    @Column(name="BusinessUnitId")
    private int businessUnitId;
    @Column(name="JobTitleId")
    private int jobTitleId;
    @Column(name="ManagerId")
    private int managerId;
}
