package com.aihealthcare.aihealthcare.repository;

import com.aihealthcare.aihealthcare.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
 }