package com.example.JSS.dto.marking;

import com.example.JSS.entity.MarksCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarksResponseDto {
    private ApplicationsResponseDto applications;
    private MarksCategory marksCategory;
    private float marks;
    private String comment;
    private String remark;
    private Date date;
}
