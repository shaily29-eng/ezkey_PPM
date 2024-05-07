package com.ezkey.projects.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "BillingTypes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillingTypeId")
    private Integer billingTypeId;

    @Column(name = "BillingTypeName")
    private String billingTypeName;

    @Column(name = "CreatedOn")
    private Date createdOn;
}
