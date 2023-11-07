package com.example.JSS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mark_id;
    @ManyToOne
    private Applications applications;
    @OneToOne
    private MarksCategory marksCategory;
    private float marks;
    private String comment;
    private String remark;
    private Date date;
}
