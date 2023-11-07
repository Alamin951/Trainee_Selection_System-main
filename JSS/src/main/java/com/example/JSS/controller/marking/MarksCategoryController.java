package com.example.JSS.controller.marking;

import com.example.JSS.dto.marking.MarksCategoryResponseDto;
import com.example.JSS.entity.MarksCategory;
import com.example.JSS.service.marking.MarksCategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marks-category")
@RequiredArgsConstructor
public class MarksCategoryController {
    private final MarksCategoryService marksCategoryService;
    @PostMapping
    public ResponseEntity<MarksCategory> createMarksCategory(@RequestBody MarksCategory marksCategory){
        return ResponseEntity.ok(marksCategoryService.createMarksCategory(marksCategory));
    }
    @GetMapping
    private ResponseEntity<List<MarksCategoryResponseDto>> getAllCategory(){

        return ResponseEntity.ok(marksCategoryService.getAllCategory());
    }
}
