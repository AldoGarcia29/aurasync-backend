package com.aurasync.aura.search.service;

import com.aurasync.aura.search.document.SearchDocument;
import com.aurasync.aura.search.repository.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final SearchRepository repository;

    public SearchService(SearchRepository repository) {
        this.repository = repository;
    }

    public List<SearchDocument> buscar(String texto) {

        if (texto == null || texto.isBlank()) {
            List<SearchDocument> documentos = new ArrayList<>();

            repository.findAll().forEach(documentos::add);

            return documentos;
        }

        return repository.buscar(texto.trim());
    }
}