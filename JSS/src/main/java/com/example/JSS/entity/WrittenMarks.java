package com.example.JSS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "written_marks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WrittenMarks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writtenMarkId;

    @Column(name = "participant_code", unique = true)
    private String participantCode;
    @ManyToOne
    private MarksCategory marksCategory;
    private float mark;
    private String comment;
}
