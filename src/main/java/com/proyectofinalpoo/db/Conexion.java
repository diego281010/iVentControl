package com.proyectofinalpoo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:5432/postgres";

    private static final String USER =
            "postgres.zlubsakadiepinylvcpg";

    private static final String PASSWORD =
            "#ProyectoPoo";

        public static Connection getConnection() {
            try {
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos." + e.getMessage());
                System.out.println("Motivo: " + e.getMessage());
                return null;
            }
        }
    }