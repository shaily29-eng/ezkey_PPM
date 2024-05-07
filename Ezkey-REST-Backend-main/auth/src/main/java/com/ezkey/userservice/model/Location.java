package com.ezkey.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Airports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @Column(name="LocationId")
    private String locationId;
    @Column(name="Type")
    private String type;
    @Column(name="Name")
    private String name;
    @Column(name="Elevation")
    private Integer elevation;
    @Column(name="Country")
    private String country;
    @Column(name="Region")
    private String region;
    @Column(name="Municipality")
    private String municipality;
    @Column(name="Latitude")
    private String latitude;
    @Column(name="Longitude")
    private String longitude;
}
