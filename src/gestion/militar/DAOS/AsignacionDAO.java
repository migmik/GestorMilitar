package gestion.militar.DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Excepciones.PersistenciaException;
import gestion.militar.Modelos.Asignacion;
import gestion.militar.Modelos.Cuartel;
import gestion.militar.Modelos.Oficial;
import gestion.militar.Repositorios.AsignacionRepositorio;

public class AsignacionDAO implements AsignacionRepositorio {
    protected Connection conexion;

    public AsignacionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void crear(Asignacion asignacion) {
        String consulta = "INSERT INTO asignaciones (oficiales_codigo, cuarteles_codigo) VALUES (?, ?)";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, asignacion.getOficial().getCodigo());
            sentencia.setInt(2, asignacion.getCuartel().getCodigo());
            sentencia.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntidadDuplicadaException(
                    "El oficial ya posee una asignacion activa o el cuartel ya tiene un oficial asignado.");
        } catch (SQLException e) {
            throw new PersistenciaException("Error al guardar en la base de datos: " + e.getMessage(), e);
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

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
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
            throw new PersistenciaException("Error tecnico en la base de datos: " + e.getMessage(), e);
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

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta);
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
            throw new PersistenciaException("Error al listar las asignaciones de la base de datos: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void actualizar(Asignacion entidad) {
        String consulta = "UPDATE asignaciones SET cuarteles_codigo = ? WHERE oficiales_codigo = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, entidad.getCuartel().getCodigo());
            sentencia.setInt(2, entidad.getOficial().getCodigo());
            int filasModificadas = sentencia.executeUpdate();
            if (filasModificadas == 0) {
                throw new EntidadNoEncontradaException("No se encontro la asignacion a actualizar.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntidadDuplicadaException("El cuartel ya tiene un oficial asignado.");
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar la asignacion en la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer codigo) {
        String consulta = "DELETE FROM asignaciones WHERE oficiales_codigo = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigo);
            int filasEliminadas = sentencia.executeUpdate();
            if (filasEliminadas == 0) {
                throw new EntidadNoEncontradaException("No se encontro la asignacion a eliminar.");
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar la asignacion de la base de datos: " + e.getMessage(), e);
        }
    }

    public int contarAsignacionesPorCuartel(int codigoCuartel) {
        String consulta = "SELECT COUNT(*) FROM asignaciones WHERE cuarteles_codigo = ?";
        int cantidad = 0;
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigoCuartel);
            try (ResultSet resultSet = sentencia.executeQuery()) {
                if (resultSet.next()) {
                    cantidad = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al contar asignaciones en la base de datos: " + e.getMessage(), e);
        }
        return cantidad;
    }

    public Asignacion consultarAsignacionPorCodigoDeCuartel(int codigoCuartel) {
        String consulta = "SELECT a.oficiales_codigo, a.cuarteles_codigo, "
                + "o.nombre AS o_nombre, o.apellido AS o_apellido, o.dni AS o_dni, "
                + "c.nombre AS c_nombre, c.capacidad "
                + "FROM asignaciones a "
                + "INNER JOIN oficiales o ON a.oficiales_codigo = o.codigo "
                + "INNER JOIN cuarteles c ON a.cuarteles_codigo = c.codigo "
                + "WHERE a.cuarteles_codigo = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigoCuartel);

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

                    return new Asignacion(oficial, cuartel);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar la asignacion por cuartel: " + e.getMessage(), e);
        }

        throw new EntidadNoEncontradaException("No se encontro ninguna asignacion para el cuartel " + codigoCuartel);
    }

    public String getTabla() {
        return "asignaciones";
    }
}
