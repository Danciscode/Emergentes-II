package com.emergentes.utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectaBD {

    static String driver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/bd_blog";
    static String usuario = "root";
    static String password = "";
    
    protected Connection conn = null;

    public ConectaBD() {
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, password);
            if (conn != null){
            System.out.println("Conexion OK"+ conn);
            }
        }catch(SQLException ex){
            System.out.println("Error de SQL"+ ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Falta Driver"+ ex.getMessage()); 
        }
    }
        public Connection conectar(){
            return conn;
        }
        public void desconectar(){
        try {
            System.out.println("Cerrando BD");
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error de SQL"+ ex.getMessage());
        }
        }
    }
    
    

