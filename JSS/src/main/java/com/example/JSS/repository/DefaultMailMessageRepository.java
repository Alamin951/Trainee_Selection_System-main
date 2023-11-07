package com.example.JSS.repository;

import com.example.JSS.entity.DefaultMailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultMailMessageRepository extends JpaRepository<DefaultMailMessage,Long> {
    DefaultMailMessage findByName(String name);
}
