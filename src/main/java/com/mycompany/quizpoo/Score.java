/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quizpoo;

/**
 *
 * @author Alessandro
 */
public class Score {
    private int id;
    private int idUsuario;
    private int pontos;
    private java.sql.Date dataTentativa;

    // Construtores, getters e setters
    public Score(int id, int idUsuario, int pontos, java.sql.Date dataTentativa) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.pontos = pontos;
        this.dataTentativa = dataTentativa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public java.sql.Date getDataTentativa() {
        return dataTentativa;
    }

    public void setDataTentativa(java.sql.Date dataTentativa) {
        this.dataTentativa = dataTentativa;
    }
}

