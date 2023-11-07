package com.example.JSS.dto.marking;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WrittenMarksDto {
    private String participantCode;
    private Long evaluatorId;
    private float mark;
    private String comment;
}
