package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("user")
public interface UserRepository extends JpaRepository<User, Long> {
}