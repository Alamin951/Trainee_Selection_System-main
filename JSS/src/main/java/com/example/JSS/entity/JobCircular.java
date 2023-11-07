package com.example.JSS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "job_circular")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobCircular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long circularId;
    private String circularName;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date openDate;
    @Temporal(TemporalType.DATE)
    private Date applicationDeadline;
}
