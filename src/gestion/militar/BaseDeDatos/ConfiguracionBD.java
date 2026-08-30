package gestion.militar.BaseDeDatos;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConfiguracionBD {
    private static final String CONF_PATH = "config/db.properties";

    public static Connection conexion() {
        Properties propiedades = new Properties();
        try (FileInputStream entrada = new FileInputStream(CONF_PATH)) {
            propiedades.load(entrada);
            String url = propiedades.getProperty("db.url");
            String user = propiedades.getProperty("db.user");
            String password = propiedades.getProperty("db.password");
            return DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer la configuracion de la base de datos.", e);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos: " + e.getMessage(), e);
        }
    }
}
