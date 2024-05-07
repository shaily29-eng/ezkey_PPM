package com.ezkey.projects.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Industry")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Industry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IndustryId")
    private Integer industryId;

    @Column(name = "IndustryName")
    private String industryName;
}