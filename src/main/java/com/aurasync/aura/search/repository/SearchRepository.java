package com.aurasync.aura.search.repository;

import com.aurasync.aura.search.document.SearchDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository
        extends ElasticsearchRepository<SearchDocument, String> {

    @Query("""
        {
          "multi_match": {
            "query": "?0",
            "fields": ["titulo^2", "descripcion"]
          }
        }
        """)
    List<SearchDocument> buscar(String texto);
}