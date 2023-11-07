package com.example.JSS.repository.marking;

import com.example.JSS.entity.WrittenMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WrittenMarksRepository extends JpaRepository<WrittenMarks,Long> {
    Optional<WrittenMarks> findByParticipantCode(String participantCode);
}
