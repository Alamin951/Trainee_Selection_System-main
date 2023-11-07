package com.example.JSS.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDto {
    private Long applicationId;
    private String status;
}
