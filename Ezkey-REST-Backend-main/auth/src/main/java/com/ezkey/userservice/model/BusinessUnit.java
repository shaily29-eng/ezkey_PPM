package com.ezkey.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "BusinessUnits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUnit {
    @Id
    @Column(name = "BusinessUnitId")
    private int businessUnitId;
    @Column(name = "BusinessUnitName")
    private String businessUnitName;
    @Column(name = "CreatedOn")
    private Date CreatedOn;
}
