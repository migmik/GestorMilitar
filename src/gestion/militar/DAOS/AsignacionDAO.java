package gestion.militar.DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gestion.militar.BaseDeDatos.ConfiguracionBD;
import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Modelos.Asignacion;
import gestion.militar.Modelos.Cuartel;
import gestion.militar.Modelos.Oficial;

public class AsignacionDAO implements GenericoDAO<Asignacion, Integer> {
    protected Connection conexion;

    public AsignacionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void crear(Asignacion asignacion) {
        String consulta = "INSERT INTO asignaciones (oficiales_codigo, cuarteles_codigo) VALUES (?, ?)";
        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, asignacion.getOficial().getCodigo());
            sentencia.setInt(2, asignacion.getCuartel().getCodigo());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new EntidadDuplicadaException(
                        "El oficial ID " + asignacion.getOficial().getCodigo()
                                + " ya posee una asignación activa o el cuartel ya tiene un oficial asignado.");
            }
            throw new RuntimeException("Error técnico en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public Optional<Asignacion> encontrarPorID(Integer codigoOficial) {
        String consulta = "SELECT a.oficiales_codigo, a.cuarteles_codigo, "
                + "o.nombre AS o_nombre, o.apellido AS o_apellido, o.dni AS o_dni, "
                + "c.nombre AS c_nombre, c.capacidad "
                + "FROM asignaciones a "
                + "INNER JOIN oficiales o ON a.oficiales_codigo = o.codigo "
                + "INNER JOIN cuarteles c ON a.cuarteles_codigo = c.codigo "
                + "WHERE a.oficiales_codigo = ?";

        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigoOficial);
            try (ResultSet resultSet = sentencia.executeQuery()) {
                if (resultSet.next()) {
                    Oficial oficial = new Oficial(
                            resultSet.getInt("oficiales_codigo"),
                            resultSet.getString("o_dni"),
                            resultSet.getString("o_apellido"),
                            resultSet.getString("o_nombre"));
                    Cuartel cuartel = new Cuartel(
                            resultSet.getInt("cuarteles_codigo"),
                            resultSet.getString("c_nombre"),
                            resultSet.getInt("capacidad"));
                    return Optional.of(new Asignacion(oficial, cuartel));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error técnico en la base de datos: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Asignacion> encontrarTodos() {
        List<Asignacion> lista = new ArrayList<>();
        String consulta = "SELECT a.oficiales_codigo, a.cuarteles_codigo, "
                + "o.nombre AS o_nombre, o.apellido AS o_apellido, o.dni AS o_dni, "
                + "c.nombre AS c_nombre, c.capacidad "
                + "FROM asignaciones a "
                + "INNER JOIN oficiales o ON a.oficiales_codigo = o.codigo "
                + "INNER JOIN cuarteles c ON a.cuarteles_codigo = c.codigo";

        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                Oficial oficial = new Oficial(
                        resultSet.getInt("oficiales_codigo"),
                        resultSet.getString("o_dni"),
                        resultSet.getString("o_apellido"),
                        resultSet.getString("o_nombre"));
                Cuartel cuartel = new Cuartel(
                        resultSet.getInt("cuarteles_codigo"),
                        resultSet.getString("c_nombre"),
                        resultSet.getInt("capacidad"));
                lista.add(new Asignacion(oficial, cuartel));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las asignaciones de la base de datos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Asignacion entidad) {
        String consulta = "UPDATE asignaciones SET cuarteles_codigo = ? WHERE oficiales_codigo = ?";
        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, entidad.getCuartel().getCodigo());
            sentencia.setInt(2, entidad.getOficial().getCodigo());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la asignación en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer codigo) {
        String consulta = "DELETE FROM asignaciones WHERE oficiales_codigo = ?";
        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigo);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la asignación de la base de datos: " + e.getMessage());
        }
    }

    public String getTabla() {
        return "asignaciones";
    }
}
