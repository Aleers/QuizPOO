/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quizpoo;

/**
 *
 * @author Alessandro
 */
import java.sql.*;

public class ConexaoBanco {
    //private static final String URL = "jdbc:mysql://localhost:3306/nome_do_banco";
    //private static final String USUARIO = "root";  // Coloque seu usuário do banco
    //private static final String SENHA = "senha";  // Coloque sua senha do banco
    
    private static final String servidor = "DESKTOP-V34U8GH\\SQLEXPRESS";
    private static final String user = "sa";
    private static final String password = "SQLusf123@";
    private static final String banco = "TesteJava";
    private static final String url = "jdbc:sqlserver://" + servidor + ";database=" + banco + ";encrypt=false;user=";
    
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

