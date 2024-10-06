package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAll();
    Optional<Message> findById(Integer messageId);
    List<Message> existsByPostedBy(Integer postedBy);
}

// "boolean existsById(Integer messsageId);" is unnecessary cuz we got Optional to handle null.
// JpaRepository already includes .save() for create and update, no need to write it.
// it also already includes .delete(), no need to write it.
