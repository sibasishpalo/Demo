package com.example.demo.service;

import com.example.demo.config.ESConfigurations;
import com.example.demo.model.Review;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Service
@Slf4j
public class SearchService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ESConfigurations esConfig;

    @Value("${es.searchUri}")
    String searchUri;

    @Cacheable(cacheNames = "review")
    public Review queryES(String reviewer)
            throws JsonProcessingException {
        log.info("Fetching reviewer information from ES for Id {} ", reviewer);
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"reviewer\": \""+reviewer+"\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.socket.timeout",3000)
                        .setParam("http.connection.timeout", 3000));

        RestAssured.baseURI = esConfig.getProtocol() + "://" + esConfig.getHost() + ":" + esConfig.getPort();
        Response response = given()
                .config(config)
                .header("Content-type", "application/json")
                .and()
                .body(query)
                .when()
                .get(searchUri)
                .then()
                .extract().response();
        List<Map<Object, Object>> paths = (List<Map<Object, Object>>) response.jsonPath().getMap("hits").get("hits");

        Map<Object, Object> docs = (Map<Object, Object>) paths.get(0).get("_source");

        return objectMapper.readValue(objectMapper.writeValueAsString(docs), Review.class);
    }
}



