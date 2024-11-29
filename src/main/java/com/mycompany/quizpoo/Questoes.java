/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.quizpoo;

/**
 *
 * @author Alessandro
 */

import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class Questoes extends javax.swing.JFrame {
    
    private Set<Integer> perguntasRespondidas = new HashSet<>();  // Conjunto para armazenar as perguntas já exibidas
    private int totalDeQuestoes = 10;  // Supondo que você tenha 10 questões no total
    private int perguntasExibidas = 0;  // Contador de perguntas exibidas
    private int pontos = 0;  // Contador de pontos
    private int respostaUsuario;  // Variável para armazenar a resposta do usuário
    private ButtonGroup group;

    // Lista para armazenar as respostas do usuário
    private ArrayList<Integer> respostasUsuario = new ArrayList<>();
    
    public void atualizaListaBanco() {
    Random r = new Random();
    int qntquestoes = r.nextInt(10) + 1; 
    try (Connection conn = Main.conexaoBanco(); 
         Statement cmd = conn.createStatement()) {

        String sql = "SELECT * FROM Questoes WHERE id = " + qntquestoes + ";";
        System.out.println(qntquestoes);
        ResultSet rs = cmd.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("id");
            String pergunta = rs.getString("pergunta");
            String alternativa1 = rs.getString("alternativa1");
            String alternativa2 = rs.getString("alternativa2");
            String alternativa3 = rs.getString("alternativa3");
            String alternativa4 = rs.getString("alternativa4");
            int resposta_correta = rs.getInt("resposta_correta");

            // Cria um objeto Questao
            Questao questao = new Questao(id, pergunta, alternativa1, alternativa2, alternativa3, alternativa4, resposta_correta);
            System.out.println(questao.getAlternativaCorreta());
            
            Pergunta.setText(questao.getPergunta());
            
            
            Alternativa1.setText(questao.getAlternativa1());
            Alternativa2.setText(questao.getAlternativa2());
            Alternativa3.setText(questao.getAlternativa3());
            Alternativa4.setText(questao.getAlternativa4());


            System.out.println("Questão atualizada com sucesso!");
        } else {
            System.out.println("Nenhuma questão encontrada para o ID: " + qntquestoes);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar dados: " + e.getMessage());
    }
}

private void proximaPergunta() {
        Random r = new Random();
        int qntquestoes = r.nextInt(10) + 1;  // Gera um número aleatório entre 1 e 10

        // Verificar se a questão já foi respondida
        while (perguntasRespondidas.contains(qntquestoes)) {
            qntquestoes = r.nextInt(10) + 1;  // Gera uma nova questão se a atual já foi respondida
        }

        // Adicionar a questão ao conjunto de respondidas
        perguntasRespondidas.add(qntquestoes);
        perguntasExibidas++;  // Incrementa o contador de perguntas exibidas

        // Verificar se todas as perguntas foram respondidas
        if (perguntasExibidas >= totalDeQuestoes) {
            // Chama a tela PaginaFinal quando todas as questões foram exibidas
            abrirPaginaFinal();
            return;  // Interrompe a execução do método
        }

        // Buscar a questão no banco de dados
        try (Connection conn = Main.conexaoBanco()) {
            String sql = "SELECT * FROM Questoes WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, qntquestoes);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Obter os dados da pergunta
                    String pergunta = rs.getString("pergunta");
                    String alternativa1 = rs.getString("alternativa1");
                    String alternativa2 = rs.getString("alternativa2");
                    String alternativa3 = rs.getString("alternativa3");
                    String alternativa4 = rs.getString("alternativa4");
                    int respostaCorreta = rs.getInt("resposta_correta");

                    // Atualizar os campos da interface gráfica
                    Pergunta.setText(pergunta);  // Atualiza o texto da pergunta
                    Alternativa1.setText(alternativa1);  // Atualiza a alternativa 1
                    Alternativa2.setText(alternativa2);  // Atualiza a alternativa 2
                    Alternativa3.setText(alternativa3);  // Atualiza a alternativa 3
                    Alternativa4.setText(alternativa4);  // Atualiza a alternativa 4

                    // Salvar a resposta correta (para comparar depois)
                    respostasUsuario.add(respostaCorreta);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }

// Método para verificar a resposta do usuário
    private void verificarResposta() {
        int respostaSelecionada = 0; // Default se nenhuma opção for selecionada

        // Verifica qual alternativa foi escolhida
        if (Alternativa1.isSelected()) {
            respostaSelecionada = 1;
        } else if (Alternativa2.isSelected()) {
            respostaSelecionada = 2;
        } else if (Alternativa3.isSelected()) {
            respostaSelecionada = 3;
        } else if (Alternativa4.isSelected()) {
            respostaSelecionada = 4;
        }

        // Verifica se a resposta está correta e incrementa os pontos
        if (respostaSelecionada == respostasUsuario.get(perguntasExibidas - 1)) {
            pontos++;  // Incrementa os pontos se a resposta estiver correta
        }

        // Chama o método para carregar a próxima pergunta
        proximaPergunta();
    }

   // Método para abrir a tela PaginaFinal
    private void abrirPaginaFinal() {
        // Passa os pontos para o construtor de PaginaFinal
        PaginaFinal paginaFinal = new PaginaFinal(pontos);  // Passando os pontos como parâmetro
        paginaFinal.setVisible(true);  // Exibe a tela de final
        this.setVisible(false);  // Fecha a tela atual (Quiz)
        this.dispose();  // Libera os recursos da janela atual
    }

    
    public Questoes() {
        initComponents();
        atualizaListaBanco();

        group = new ButtonGroup();
        
        group.add(Alternativa1);
        group.add(Alternativa2);
        group.add(Alternativa3);
        group.add(Alternativa4);

        // Carregar a primeira questão
        //carregarQuestao();
        proximaPergunta();
        
    }
    
//     private void carregarQuestao() {
//        if (indiceAtual < questoes.size()) {
//            Questao questao = questoes.get(indiceAtual);
//            Pergunta.setText(questao.getPergunta());
//            Alternativa1.setText(questao.getAlternativa1());
//            Alternativa2.setText(questao.getAlternativa2());
//            Alternativa3.setText(questao.getAlternativa3());
//            Alternativa4.setText(questao.getAlternativa4());
//        } else {
//            // Exibir uma mensagem de que acabou as questões
//            Pergunta.setText("Fim do Quiz!");
//        }
//    }

//
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoAlternativas = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        Alternativa1 = new javax.swing.JRadioButton();
        Alternativa2 = new javax.swing.JRadioButton();
        Alternativa3 = new javax.swing.JRadioButton();
        Alternativa4 = new javax.swing.JRadioButton();
        Pergunta = new javax.swing.JLabel();
        ImagemQuiz = new javax.swing.JLabel();
        proximo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 255));

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        grupoAlternativas.add(Alternativa1);
        Alternativa1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        Alternativa1.setForeground(new java.awt.Color(0, 0, 0));
        Alternativa1.setText("Alternativa 1");
        Alternativa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa1ActionPerformed(evt);
            }
        });

        grupoAlternativas.add(Alternativa2);
        Alternativa2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        Alternativa2.setForeground(new java.awt.Color(0, 0, 0));
        Alternativa2.setText("Alternativa 3");
        Alternativa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa2ActionPerformed(evt);
            }
        });

        grupoAlternativas.add(Alternativa3);
        Alternativa3.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        Alternativa3.setForeground(new java.awt.Color(0, 0, 0));
        Alternativa3.setText("Alternativa 2");
        Alternativa3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa3ActionPerformed(evt);
            }
        });

        grupoAlternativas.add(Alternativa4);
        Alternativa4.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        Alternativa4.setForeground(new java.awt.Color(0, 0, 0));
        Alternativa4.setText("Alternativa 4");
        Alternativa4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa4ActionPerformed(evt);
            }
        });

        Pergunta.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        Pergunta.setForeground(new java.awt.Color(0, 0, 0));
        Pergunta.setText("Pergunta");

        ImagemQuiz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quiz-logo-time-label-question-260nw-2299277831.jpg"))); // NOI18N

        proximo.setBackground(new java.awt.Color(153, 153, 153));
        proximo.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        proximo.setForeground(new java.awt.Color(0, 0, 0));
        proximo.setText("Proximo");
        proximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proximoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ImagemQuiz))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Pergunta, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Alternativa1)
                            .addComponent(Alternativa2)
                            .addComponent(Alternativa4)
                            .addComponent(Alternativa3))
                        .addGap(0, 150, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(proximo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ImagemQuiz)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Pergunta, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(Alternativa1)
                .addGap(18, 18, 18)
                .addComponent(Alternativa3)
                .addGap(18, 18, 18)
                .addComponent(Alternativa2)
                .addGap(18, 18, 18)
                .addComponent(Alternativa4)
                .addGap(18, 18, 18)
                .addComponent(proximo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Alternativa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alternativa1ActionPerformed

    private void Alternativa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alternativa2ActionPerformed

    private void Alternativa3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alternativa3ActionPerformed

    private void Alternativa4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alternativa4ActionPerformed

    private void proximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proximoActionPerformed
            // TODO add your handling code here:
        //proximaPergunta();
        verificarResposta(); 
    }//GEN-LAST:event_proximoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Alternativa1;
    private javax.swing.JRadioButton Alternativa2;
    private javax.swing.JRadioButton Alternativa3;
    private javax.swing.JRadioButton Alternativa4;
    private javax.swing.JLabel ImagemQuiz;
    private javax.swing.JLabel Pergunta;
    private javax.swing.ButtonGroup grupoAlternativas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton proximo;
    // End of variables declaration//GEN-END:variables

}
