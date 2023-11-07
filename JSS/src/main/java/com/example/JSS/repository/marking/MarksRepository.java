package com.example.JSS.repository.marking;

import com.example.JSS.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {
    Optional<Marks> findByApplicationsApplicationIdAndMarksCategoryCategoryId(Long applicationId, Long categoryId);
    Optional<List<Marks>> findByApplicationsApplicationId(Long applicationId);

}
