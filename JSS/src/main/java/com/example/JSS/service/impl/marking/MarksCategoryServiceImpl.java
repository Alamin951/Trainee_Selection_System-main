package com.example.JSS.service.impl.marking;

import com.example.JSS.dto.marking.MarksCategoryResponseDto;
import com.example.JSS.entity.MarksCategory;
import com.example.JSS.repository.marking.MarksCategoryRepository;
import com.example.JSS.service.marking.MarksCategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarksCategoryServiceImpl implements MarksCategoryService {
    private final MarksCategoryRepository marksCategoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public MarksCategory createMarksCategory(MarksCategory marksCategory) {
        return marksCategoryRepository.save(marksCategory);
    }
    @Override
    public List<MarksCategoryResponseDto> getAllCategory(){
        List<MarksCategory> marksCategory=marksCategoryRepository.findAll();
        if (marksCategory.isEmpty()){
            throw new EntityNotFoundException("No Marks Category Available!!!");
        }
        return marksCategory.stream()
                .map(this::convertToMarksCategoryDto)
                .collect(Collectors.toList());
    }
    private MarksCategoryResponseDto convertToMarksCategoryDto(MarksCategory marksCategory){
        return modelMapper.map(marksCategory, MarksCategoryResponseDto.class);
    }
}
