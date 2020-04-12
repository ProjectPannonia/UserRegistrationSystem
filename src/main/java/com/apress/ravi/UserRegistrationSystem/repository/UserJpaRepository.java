package com.apress.ravi.UserRegistrationSystem.repository;

import com.apress.ravi.UserRegistrationSystem.dto.UserDTO;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDTO,Long> {
    UserDTO findByName(String name);

    @Override
    List<UserDTO> findAll();
}
