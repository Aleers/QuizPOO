/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quizpoo;

/**
 *
 * @author Alessandro
 */
public class Usuario {
    private int id;
    private String nome;
    private int RA;

    // Novo construtor que aceita apenas nome e RA, e define um id automático (por exemplo, 0)
    public Usuario(String nome, int RA) {
        this.id = 0;  // Ou alguma lógica para gerar o id
        this.nome = nome;
        this.RA = RA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRA() {
        return RA;
    }

    public void setRA(int RA) {
        this.RA = RA;
    }
}
