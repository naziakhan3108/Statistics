package com.n26.statistics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistics.entity.Statistics;
import com.n26.statistics.entity.Summary;
import com.n26.statistics.service.StatisticsService;
import com.n26.statistics.service.StatisticsServiceBean;

/**
 * Created by Nazia
 */
@RestController
public class StatisticsController {

	private StatisticsService statsService;

	public StatisticsController(StatisticsService statsService) {
		this.statsService = statsService;
	}

	@GetMapping("/statistics")
	public Summary get() {
		return statsService.getSummary();
	}

	@PostMapping("/transactions")
	public ResponseEntity add(@RequestBody Statistics statistics) {
		try {
			statsService.add(statistics);
			return new ResponseEntity(HttpStatus.CREATED);
		} catch (StatisticsServiceBean.StatAlreadyExpired e) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
}
