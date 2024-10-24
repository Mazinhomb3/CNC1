package br.com.igpaz.dal;

import java.sql.*;
import java.sql.DriverManager;

public class ModuloConexao {

    //conexão
    public static Connection conector() {
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://br210.hostgator.com.br:3306/myrepo89_db_relatorios";
        String user = "myrepo89_mazinho";
        String password = "#(J4B7U?x9^C";

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
