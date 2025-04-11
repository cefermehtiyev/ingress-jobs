package com.example.ingressjobs.service.concurate;

import com.example.ingressjobs.annotation.Log;
import com.example.ingressjobs.dao.entity.JobEntity;
import com.example.ingressjobs.dao.repository.JobRepository;
import com.example.ingressjobs.exception.ErrorMessage;
import com.example.ingressjobs.exception.LoginFailedException;
import com.example.ingressjobs.service.abstraction.ScraperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.ingressjobs.model.constants.LoginConstants.LOGIN_POST_URL;
import static com.example.ingressjobs.model.constants.LoginConstants.LOGIN_URL;
import static com.example.ingressjobs.model.constants.LoginConstants.USER_EMAIL;
import static com.example.ingressjobs.model.constants.LoginConstants.USER_PASSWORD;
@Log
@Slf4j
@Service
@RequiredArgsConstructor
public class ScraperServiceHandler implements ScraperService {

    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;
    private static String csrfToken = "";
    private static String sessionId = "";


    public void scrapeAndSaveJobs() {
        loginToDjinni();

        int pageCount = 0;
        while (true) {

            pageCount++;
            String url = "https://djinni.co/jobs/?primary_keyword=Java&employment=remote&page=" + pageCount;
            System.out.println("Fetching: " + url);


            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            headers.set("Cookie", "csrftoken=" + csrfToken + "; sessionid=" + sessionId);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String htmlContent = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
            if (htmlContent == null || htmlContent.isEmpty()) {
                return;
            }

            Document doc = Jsoup.parse(htmlContent);
            Elements jobElements = doc.select("li[id^=job-item-]");

            List<JobEntity> jobEntities = new ArrayList<>();
            LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

            for (var jobElement : jobElements) {
                String dateText = jobElement.select("span.text-nowrap[title]").last().attr("title");
                LocalDate postedDate = parseFullDate(dateText);

                if (postedDate != null && postedDate.isBefore(threeMonthsAgo)) {
                    return;
                }

                String jobTitle = jobElement.select("a.job-item__title-link").text();
                String locationText = jobElement.select(".location-text").text();
                if (!(locationText.equals("Worldwide"))) {
                    continue;
                }

                String companyName = jobElement.select("a[data-analytics=company_page]").text();
                String sourceUrl = "https://djinni.co" + jobElement.select("a.job-item__title-link").attr("href");
                String companyLogoUrl = jobElement.select("img.userpic-image.userpic-image_img").attr("src");
                String metaInfo = jobElement.select(".fw-medium").text();
                String experienceLevel = metaInfo.contains("Intermediate") ? "Intermediate" : "Junior/Senior";
                String salaryRange = "";
                String experience = "";
                String description = "";

                String htmlContent2 = getJobDetailsPage(sourceUrl);
                if (htmlContent2 == null || htmlContent2.isEmpty()) {
                    continue;
                }

                Document doc2 = Jsoup.parse(htmlContent2);
                Elements jobElements2 = doc2.select("div.page-content");

                for (var sourceElement : jobElements2) {
                    experience = sourceElement.select("strong.font-weight-600.capitalize-first-letter").text();
                    for (var el : sourceElement.select("strong.font-weight-600.capitalize-first-letter")) {
                        if (el.text().contains("$")) {
                            salaryRange = el.text();
                            break;
                        }
                    }
                    description = sourceElement.select("div.col-lg-8.order-lg-0.order-1").text();
                }

                JobEntity job = JobEntity.builder()
                        .title(jobTitle)
                        .companyName(companyName)
                        .companyLogoUrl(companyLogoUrl)
                        .location(locationText)
                        .experienceLevel(experience)
                        .description(description)
                        .jobType("Remote")
                        .requirements("N/A")
                        .educationLevel(experienceLevel)
                        .industry("Tech")
                        .postedDate(postedDate)
                        .sourceUrl(sourceUrl)
                        .salaryRange(salaryRange)
                        .build();

                jobEntities.add(job);
            }

            if (!jobEntities.isEmpty()) {
                jobRepository.saveAll(jobEntities);
            }

            if (pageCount >8){
                break;
            }

        }
    }

    private String getJobDetailsPage(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            headers.set("Cookie", "csrftoken=" + csrfToken + "; sessionid=" + sessionId);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private void loginToDjinni() {
        try {
            Connection.Response loginResponse = Jsoup.connect(LOGIN_URL)
                    .userAgent("Mozilla/5.0")
                    .method(Connection.Method.GET)
                    .execute();

            Document loginPage = loginResponse.parse();
            csrfToken = loginPage.select("input[name=csrfmiddlewaretoken]").val();
            System.out.println("CSRF Token: " + csrfToken);

            Map<String, String> formData = Map.of(
                    "email", USER_EMAIL,
                    "password", USER_PASSWORD,
                    "csrfmiddlewaretoken", csrfToken
            );

            Connection connection = Jsoup.connect(LOGIN_POST_URL)
                    .userAgent("Mozilla/5.0")
                    .method(Connection.Method.POST)
                    .data(formData)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .cookie("csrftoken", csrfToken);

            Connection.Response postResponse = connection.execute();

            if (postResponse.statusCode() == 200) {

                Map<String, String> cookies = postResponse.cookies();
                sessionId = cookies.get("sessionid");
                csrfToken = cookies.get("csrftoken");
            }
        } catch (Exception e) {
            throw new LoginFailedException(ErrorMessage.LOGIN_FAILED_EXCEPTION.getMessage());
        }
    }


    private LocalDate parseFullDate(String text) {
        try {
            if (text == null || text.isEmpty()) return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
            return LocalDateTime.parse(text, formatter).toLocalDate();
        } catch (Exception e) {
            log.info("Date parsing error");
            return null;
        }
    }
}