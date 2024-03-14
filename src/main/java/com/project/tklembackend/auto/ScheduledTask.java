package com.project.tklembackend.auto;

import com.project.tklembackend.model.StudentLog;
import com.project.tklembackend.repository.StudentLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class ScheduledTask {
    private final StudentLogRepository studentLogRepository;
    @Scheduled(cron = "0 0 0 * * *")
    public void removeOldStudentLogs() {
        Instant cutoffTime = Instant.now().minus(Duration.ofDays(30));

        List<StudentLog> logsToRemove = studentLogRepository.findByDateBefore(cutoffTime);
        studentLogRepository.deleteAll(logsToRemove);
    }
}