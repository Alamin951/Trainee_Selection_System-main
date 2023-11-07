package com.example.JSS.repository.marking;

import com.example.JSS.entity.MarksCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarksCategoryRepository extends JpaRepository<MarksCategory,Long> {
    Optional<MarksCategory> findByCategoryName(String categoryName);
}
