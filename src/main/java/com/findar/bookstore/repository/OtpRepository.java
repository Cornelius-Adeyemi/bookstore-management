package com.findar.bookstore.repository;

import com.findar.bookstore.model.entity.OtpBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpBase, Long> {


    Optional<OtpBase> findFirstByEmailAndActiveTrue(String email);
}
