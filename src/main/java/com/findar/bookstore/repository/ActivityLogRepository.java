package com.findar.bookstore.repository;

import com.findar.bookstore.model.entity.ActivityLogger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLogger, Long> {
}
