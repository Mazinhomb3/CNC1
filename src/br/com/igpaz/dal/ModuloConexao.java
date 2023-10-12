package br.com.igpaz.dal;

import java.sql.*;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ModuloConexao {

    //conexão
    public static Connection conector() {
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        //String url = "jdbc:mysql://38.50.58.96:3306/db_relatorios";
        String url = "jdbc:mysql://192.168.10.10:3306/db_relatorios";
        String user = "mazinho";
        String password = "Fbcostame1";

        try {
            
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
           
            System.out.println(e);
           
            return null;
        }

    }
}
