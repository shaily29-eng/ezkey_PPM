package com.ezkey.projects.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "ServiceLines")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ServiceLineId")
    private Integer serviceLineId;

    @Column(name = "ServiceLineName")
    private String serviceLineName;

    @Column(name = "CreatedOn")
    private Date createdOn;
}