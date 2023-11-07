package com.example.JSS.service.marking;

import com.example.JSS.dto.marking.MarksCategoryResponseDto;
import com.example.JSS.entity.MarksCategory;

import java.util.List;

public interface MarksCategoryService {
    MarksCategory createMarksCategory(MarksCategory marksCategory);
    public List<MarksCategoryResponseDto> getAllCategory();
}
