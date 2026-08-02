package com.aurasync.aura.search.config;

import com.aurasync.aura.search.document.SearchDocument;
import com.aurasync.aura.search.repository.SearchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchDataInitializer implements CommandLineRunner {

    private final SearchRepository repository;

    public SearchDataInitializer(SearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            System.out.println("El índice aurasync ya contiene documentos.");
            return;
        }

        List<SearchDocument> documentos = List.of(
            new SearchDocument(
                "1",
                "Inicio",
                "Panel principal con información de bienestar, estado emocional y recomendaciones.",
                "/"
            ),
            new SearchDocument(
                "2",
                "Bienestar",
                "Rutinas de respiración, relajación, música y recomendaciones para mejorar el bienestar.",
                "/bienestar"
            ),
            new SearchDocument(
                "3",
                "Estadísticas",
                "Consulta información de ritmo cardíaco, pasos, sueño, estrés y actividad física.",
                "/estadisticas"
            ),
            new SearchDocument(
                "4",
                "Dispositivos",
                "Administra los dispositivos, smartwatch y Smart TV vinculados con AuraSync.",
                "/dispositivos"
            ),
            new SearchDocument(
                "5",
                "Contacto",
                "Teléfono, WhatsApp, redes sociales y formulario de contacto de AuraSync.",
                "/contacto"
            ),
            new SearchDocument(
                "6",
                "Aviso de privacidad",
                "Consulta cómo AuraSync protege y utiliza la información personal del usuario.",
                "/aviso-privacidad"
            )
        );

        repository.saveAll(documentos);

        System.out.println("Contenido de AuraSync guardado en Elasticsearch.");
    }
}