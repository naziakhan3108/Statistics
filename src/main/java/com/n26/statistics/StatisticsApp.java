package com.n26.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.n26.statistics.service.StatisticsService;


@SpringBootApplication
@EnableScheduling
public class StatisticsApp {
	public static void main(String[] args) {
		SpringApplication.run(StatisticsApp.class, args);
	}

	@Autowired
	StatisticsService statsAppService;

	@Scheduled(fixedDelay = 200)
	public void clearExpiredStats() {
		statsAppService.clear();
	}
}
