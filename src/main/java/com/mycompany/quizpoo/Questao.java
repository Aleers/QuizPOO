/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quizpoo;

/**
 *
 * @author Alessandro
 */
//import java.util.List;

public class Questao {
    private int id;
    private String pergunta;
    private String alternativa1;
    private String alternativa2;
    private String alternativa3;
    private String alternativa4;
    private int alternativaCorreta;

    public Questao(int id, String pergunta, String alternativa1, String alternativa2, 
                   String alternativa3, String alternativa4, int alternativaCorreta) {
        this.id = id;
        this.pergunta = pergunta;
        this.alternativa1 = alternativa1;
        this.alternativa2 = alternativa2;
        this.alternativa3 = alternativa3;
        this.alternativa4 = alternativa4;
        this.alternativaCorreta = alternativaCorreta;
    }

    // Getters e setters
    public String getPergunta() {
        return pergunta;
    }

    public String getAlternativa1() {
        return alternativa1;
    }

    public String getAlternativa2() {
        return alternativa2;
    }

    public String getAlternativa3() {
        return alternativa3;
    }

    public String getAlternativa4() {
        return alternativa4;
    }

    public int getAlternativaCorreta() {
        return alternativaCorreta;
    }
}
