package com.aurasync.aura.search.controller;

import com.aurasync.aura.search.document.SearchDocument;
import com.aurasync.aura.search.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @GetMapping
    public List<SearchDocument> buscar(
            @RequestParam(defaultValue = "") String q
    ) {
        return service.buscar(q);
    }
}