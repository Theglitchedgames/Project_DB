package com.example.project_db;

public class Comentario {
    int id;
    String titulo;
    String texto;

    public Comentario(int id,String titulo, String texto) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getTexto() { return texto; }
}
