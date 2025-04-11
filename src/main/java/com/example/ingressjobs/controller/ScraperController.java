package com.example.ingressjobs.controller;

import com.example.ingressjobs.service.abstraction.ScraperService;
import com.example.ingressjobs.service.concurate.ScraperServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/jobs")
public class ScraperController {
    private final ScraperService scraperService;

    @PostMapping("/scrape-jobs")
    public void scrapeJobs() {
        scraperService.scrapeAndSaveJobs();
    }
}
