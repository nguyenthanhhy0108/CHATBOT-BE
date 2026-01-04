package com.example.be.repository.interfaces;

import com.example.be.model.entity.ChatUrl;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUrlRepository extends JpaRepository<ChatUrl, UUID> {

}
