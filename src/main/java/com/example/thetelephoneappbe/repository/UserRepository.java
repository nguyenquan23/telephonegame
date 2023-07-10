package com.example.thetelephoneappbe.repository;


import com.example.thetelephoneappbe.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
