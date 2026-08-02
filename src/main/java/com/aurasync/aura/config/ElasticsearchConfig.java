package com.aurasync.aura.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

@Configuration
public class ElasticsearchConfig
        extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrl;

    @Value("${aurasync.elasticsearch.api-key:}")
    private String apiKey;

    @Override
    public ClientConfiguration clientConfiguration() {
        URI endpoint = URI.create(elasticsearchUrl);

        int puerto = endpoint.getPort();

        if (puerto == -1) {
            puerto = "https".equalsIgnoreCase(
                    endpoint.getScheme()
            ) ? 443 : 9200;
        }

        String servidor =
                endpoint.getHost() + ":" + puerto;

        HttpHeaders headers = new HttpHeaders();

        if (apiKey != null && !apiKey.isBlank()) {
            headers.add(
                    "Authorization",
                    "ApiKey " + apiKey
            );
        }

        if ("https".equalsIgnoreCase(
                endpoint.getScheme()
        )) {
            return ClientConfiguration
                    .builder()
                    .connectedTo(servidor)
                    .usingSsl()
                    .withDefaultHeaders(headers)
                    .build();
        }

        return ClientConfiguration
                .builder()
                .connectedTo(servidor)
                .withDefaultHeaders(headers)
                .build();
    }
}