package com.example.demo.resource;

import com.example.demo.model.Review;
import com.example.demo.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class EndPoints {

    @Autowired
    SearchService searchService;

    @Autowired
    CacheManager cacheManager;

    @GetMapping("query")
    public Review getData(@RequestParam("reviewer") String reviewer)
            throws JsonProcessingException {
        return searchService.queryES(reviewer);
    }
}
