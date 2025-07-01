package com.example.live.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/api/public")
@Controller
public class PublicApiController {
    private static final String APIKey = "YSlsLYwqeC19bkifc6uskWPBa8dbATcLVnPfjlHa";
    private static final String APIUrl = "https://api.nasa.gov/planetary/apod";
    private static final String APIUrl2 = "https://api.nasa.gov/neo/rest/v1/feed";

    private static final String startDate = "2023-10-01"; // Example start date
    private static final String endDate = "2023-10-08"; // Example end date
    private static final LocalDate date = LocalDate.now();

    private static final String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    private static final String testDate = "2025-04-06"; // Example test date for the API

    @GetMapping("/astronomy-picture-of-the-day")
    public static String getAstronomyPictureOfTheDay() throws Exception, IOException {


        String url = APIUrl + "?api_key=" + APIKey + "&date=" + testDate;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(url))

                .build();

        // Execute the request and handle the response.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            // Parse the response to extract the image URL or other relevant data.
            String responseBody = response.body();

            return "Astronomy Picture of the Day: " + responseBody; // Placeholder for actual image URL
        } else if (response.statusCode() == 400) {
            throw new IOException("Bad Request: Please check the parameters provided. Status code: " + response.statusCode() + "body:" + response.body() + "url:" + url);
        } else {
            throw new IOException("Failed to fetch data from NASA API. Status code: " + response.statusCode() + "body:" + response.body() + "url:" + url);
        }


    }

    @GetMapping("/near-earth-objects")
    public String getNearEarthObjects() throws Exception, IOException {
        // Make an HTTP GET request to the NASA API using the APIKey and APIUrl2.

        String url = APIUrl2 + "?api_key=" + APIKey + "&start_date=" + startDate + "&end_date=" + endDate;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(url)).build();
        // Execute the request and handle the response.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            // Parse the response to extract the near-Earth objects data.
            String responseBody = response.body();

            return "Near-Earth Objects: " + responseBody; // Placeholder for actual data
        } else if (response.statusCode() == 400) {
            throw new IOException("Bad Request: Please check the parameters provided. Status code: " + response.statusCode() + "body:" + response.body() + "url:" + url);
        } else {
            throw new IOException("Failed to fetch data from NASA API. Status code: " + response.statusCode() + "body:" + response.body() + "url:" + url);
        }

    }
}
