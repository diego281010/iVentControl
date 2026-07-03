package com.proyectofinalpoo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // TODO: conectar a la base de datos en la nube (datos placeholder)
    private static final String url = "jdbc:postgesql://localhost:5432/smth";
    private static final String user = "postgres";
    private static final String pass = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
