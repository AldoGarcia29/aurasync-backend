package com.aurasync.aura.search.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(
        indexName = "aurasync",
        createIndex = false
)
public class SearchDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String titulo;

    @Field(type = FieldType.Text)
    private String descripcion;

    @Field(type = FieldType.Keyword)
    private String ruta;

    public SearchDocument() {
    }

    public SearchDocument(
            String id,
            String titulo,
            String descripcion,
            String ruta
    ) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ruta = ruta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}