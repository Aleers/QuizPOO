package com.mycompany.quizpoo;

import java.sql.*;

public class Main {
    
    public static Connection conexaoBanco(){
        String servidor = "DESKTOP-V34U8GH\\SQLEXPRESS";
        String user = "sa";
        String password = "SQLusf123@";
        String banco = "TesteJava";
        String url = "jdbc:sqlserver://" + servidor + ";database=" + banco + ";encrypt=false;user=" + user + ";password=" + password;
        //String url = "jdbc:sqlserver://server-db-test-poo.database.windows.net:1433;database=db-test-poo;user=poouser@server-db-test-poo;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=300";
        
        try {
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Conexao bem-sucedida!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }
    public static void modificaTabelaSQL(String comando){
        Connection conn = conexaoBanco();
        if (conn != null) {
            try {
                Statement cmd = conn.createStatement();           
                cmd.executeUpdate(comando);
                System.out.println("Dados atualizados com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao interagir com dados: " + e.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }      
    }
    
    public static void main(String[] args) {
        
        Quiz quiz = new Quiz();
        quiz.setVisible(true);
        //GameManager.iniciarJogo();
        // Definir o look-and-feel da interface
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Caso haja algum erro, exibe a exceção
        }

        // Criar e exibir o JFrame da classe Quiz
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Instanciar o JFrame (Quiz) diretamente
                //Quiz quizFrame = new Quiz();
                //quizFrame.setLocationRelativeTo(null);  // Centraliza a janela
                //quizFrame.setVisible(true);  // Torna o JFrame visível
            }
        });
    }
}
