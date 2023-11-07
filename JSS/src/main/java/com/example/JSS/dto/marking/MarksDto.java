package com.example.JSS.dto.marking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarksDto {
    private Long applicationId;
    private Long marksCategoryId;
    private float marks;
    private String comment;
    private String remark;
    private Date date;
}
