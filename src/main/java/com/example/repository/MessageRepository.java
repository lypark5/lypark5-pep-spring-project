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
  boolean existsById(Integer messageId);     // for delete
  List<Message> findByPostedBy(Integer postedBy);   // find all messages by user(postedBy)
}

// better to use Integer in this layer, standard practice in JPA, allows nullable.
// boolean existsByPostedBy(Integer postedBy);  
  // this is too complicated, just use existsByAccountId in AccountRepo in
// JpaRepository already includes .save() for create and update, so we don't need to write it.
// it also already includes .delete() so we don't need to write it.
