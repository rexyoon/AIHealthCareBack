package com.aihealthcare.aihealthcare.repository;

import com.aihealthcare.aihealthcare.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
 }