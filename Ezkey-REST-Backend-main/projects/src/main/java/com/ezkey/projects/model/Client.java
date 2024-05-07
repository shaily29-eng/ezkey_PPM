package com.ezkey.projects.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "EzkeyClient")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClientId")
    private Integer clientId;

    @Column(name = "ClientName")
    private String clientName;

    @Column(name = "LocationId")
    private String locationId;

    @Column(name = "CreatedOn")
    private Date createdOn;
}