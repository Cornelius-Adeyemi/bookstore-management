package com.findar.bookstore.repository;

import com.findar.bookstore.enums.Role;
import com.findar.bookstore.model.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users,Long > {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUserName(String username);
    Optional<Users> findFirstByRole(Role role);

    Page<Users> findAll(Pageable pageable);
}
