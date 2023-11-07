package com.example.JSS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCircularDto {
    private String circularName;
    private String description;
    private Date openDate;
    private Date applicationDeadline;
}
