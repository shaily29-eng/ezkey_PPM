package com.ezkey.projects.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ProjectServiceLines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectServiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectServiceLinesId")
    private Integer projectServiceLineId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProjectId")
    private Project project;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ServiceLineId")
    private ServiceLine serviceLine;
}
