package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByUsername(String username);
    boolean existsByAccountId(Integer accountId);
    Optional<Account> findByUsername(String username);
}

// you don't need create with JpaRepository
// <Account, Integer>: 
    // Account = type of entity this repo is managing
    // Integer = type of primary key 
// you don't need to write any concrete implementation of these methods, they are under the hood
// all these method names are established methods of JpaRepository
// Optional is a wrapper for nullable values, prevent NullPointerExceptions.
    // we use it cuz we might not find a matching account with that username.
