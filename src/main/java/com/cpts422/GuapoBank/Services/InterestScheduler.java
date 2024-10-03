package com.cpts422.GuapoBank.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class InterestScheduler {
    @Autowired
    private InterestService interestService;

    // Scheduled to run at midnight on the first day of every month.
    @Scheduled(cron = "0 0 0 1 * *")
    public void scheduleInterest() {
        interestService.applyInterestToAll();
    }
}
