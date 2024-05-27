package com.findar.bookstore.service.implementation;


import com.findar.bookstore.enums.Activity;
import com.findar.bookstore.model.entity.ActivityLogger;
import com.findar.bookstore.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityLoggerService {

    private final ActivityLogRepository activityLogRepository;

    public void save(Activity activity, String email){

        ActivityLogger activityLogger = new ActivityLogger(email, activity);

        activityLogRepository.save(activityLogger);

    }
}
