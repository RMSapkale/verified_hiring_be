package com.vhProject.repository;

import com.vhProject.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
