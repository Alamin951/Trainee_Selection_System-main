package com.example.JSS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marks_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarksCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private float minMark;
    private float maxMark;
}
