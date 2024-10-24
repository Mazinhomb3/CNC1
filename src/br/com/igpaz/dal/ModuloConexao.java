package br.com.igpaz.dal;

import java.sql.*;
import java.sql.DriverManager;

public class ModuloConexao {

    //conexão
    public static Connection conector() {
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://my-report.site:3306/myrepo89_db_relatorios";
        String user = "myrepo89_my-report";
        String password = "Fbcostame$1";

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
