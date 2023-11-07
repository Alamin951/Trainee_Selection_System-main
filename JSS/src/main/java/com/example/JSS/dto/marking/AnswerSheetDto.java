package com.example.JSS.dto.marking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSheetDto {
    private Long applicationId;
    private Long evaluatorId;
    private String participantCode;
}

