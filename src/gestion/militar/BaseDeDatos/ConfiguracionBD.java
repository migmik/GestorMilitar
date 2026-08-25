package gestion.militar.BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConfiguracionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/gestionMilitarDB";
    private static final String USER = "gestorMilitar";
    private static final String PASSWORD = "camarerodesencamaronamelo";

    public static Connection conexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }
}
