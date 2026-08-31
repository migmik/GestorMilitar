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
import gestion.militar.Modelos.Cuartel;

public class CuartelDAO implements GenericoDAO<Cuartel, Integer> {

    private Connection conexion;

    public CuartelDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void crear(Cuartel cuartel) {
        String consulta = "INSERT INTO cuarteles (nombre, capacidad) VALUES (?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta,
                java.sql.Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setString(1, cuartel.getNombre());
            sentencia.setInt(2, cuartel.getCapacidad());
            sentencia.executeUpdate();
            try (ResultSet keyGeneradas = sentencia.getGeneratedKeys()) {
                if (keyGeneradas.next()) {
                    cuartel.setCodigo(keyGeneradas.getInt(1));
                }
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntidadDuplicadaException("Ya existe un cuartel con ese nombre.");
        } catch (SQLException e) {
            throw new PersistenciaException("Error al guardar en la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cuartel> encontrarTodos() {
        List<Cuartel> lista = new ArrayList<>();
        String consulta = "SELECT * FROM cuarteles";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultSet = sentencia.executeQuery()) {

            while (resultSet.next()) {
                Cuartel cuartel = new Cuartel(
                        resultSet.getInt("codigo"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("capacidad"));

                lista.add(cuartel);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al listar desde la base de datos: " + e.getMessage(), e);
        }

        return lista;
    }

    @Override
    public Optional<Cuartel> encontrarPorID(Integer codigo) {
        String consulta = "SELECT * FROM cuarteles WHERE codigo = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigo);

            try (ResultSet resultSet = sentencia.executeQuery()) {
                if (resultSet.next()) {
                    Cuartel cuartel = new Cuartel(
                            resultSet.getInt("codigo"),
                            resultSet.getString("nombre"),
                            resultSet.getInt("capacidad"));

                    return Optional.of(cuartel);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar en la base de datos.", e);
        }

        return Optional.empty();
    }

    @Override
    public void actualizar(Cuartel cuartel) {
        String consulta = "UPDATE cuarteles SET nombre = ?, capacidad = ? WHERE codigo = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, cuartel.getNombre());
            sentencia.setInt(2, cuartel.getCapacidad());
            sentencia.setInt(3, cuartel.getCodigo());

            int filas = sentencia.executeUpdate();

            if (filas == 0) {
                throw new EntidadNoEncontradaException("No se encontro el registro a actualizar.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntidadDuplicadaException("Ya existe un cuartel con ese nombre.");
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar en la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer codigo) {
        String consulta = "DELETE FROM cuarteles WHERE codigo = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigo);
            int filasEliminadas = sentencia.executeUpdate();
            if (filasEliminadas == 0) {
                throw new EntidadNoEncontradaException("No se encontro el registro a eliminar.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new PersistenciaException("No se puede eliminar porque el registro tiene datos relacionados.", e);
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar en la base de datos: " + e.getMessage(), e);
        }
    }

    public String getTabla() {
        return "cuarteles";
    }
}
