package gestion.militar.DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Persona;

public abstract class PersonaDAO<T extends Persona>
        implements GenericoDAO<T, Integer> {

    protected Connection conexion;

    public PersonaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    protected abstract String getTabla();

    protected abstract T crearDesdeResultSet(ResultSet resultSet)
            throws SQLException;

    @Override
    public void crear(T persona) {
        String consulta = "INSERT INTO " + getTabla()
                + " (dni, nombre, apellido) VALUES (?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(
                consulta, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, persona.getDni());
            sentencia.setString(2, persona.getNombre());
            sentencia.setString(3, persona.getApellido());
            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    persona.setCodigo(claves.getInt(1));
                }
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntidadDuplicadaException("Ya existe una persona con ese DNI.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar en la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<T> encontrarTodos() {
        List<T> lista = new ArrayList<>();
        String consulta = "SELECT * FROM " + getTabla();

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultSet = sentencia.executeQuery()) {

            while (resultSet.next()) {
                lista.add(crearDesdeResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar desde la base de datos: " + e.getMessage(), e);
        }

        return lista;
    }

    @Override
    public Optional<T> encontrarPorID(Integer codigo) {
        String consulta = "SELECT * FROM " + getTabla()
                + " WHERE codigo = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigo);

            try (ResultSet resultSet = sentencia.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(crearDesdeResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar en la base de datos.", e);
        }

        return Optional.empty();
    }

    @Override
    public void actualizar(T persona) {
        String consulta = "UPDATE " + getTabla()
                + " SET dni = ?, apellido = ?, nombre = ?"
                + " WHERE codigo = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, persona.getDni());
            sentencia.setString(2, persona.getApellido());
            sentencia.setString(3, persona.getNombre());
            sentencia.setInt(4, persona.getCodigo());
            int filasActualizadas = sentencia.executeUpdate();
            if (filasActualizadas == 0) {
                throw new EntidadNoEncontradaException("No se encontro el registro a actualizar.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntidadDuplicadaException("Ya existe una persona con ese DNI.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar en la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer codigo) {
        String consulta = "DELETE FROM " + getTabla()
                + " WHERE codigo = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigo);
            int filasEliminadas = sentencia.executeUpdate();
            if (filasEliminadas == 0) {
                throw new EntidadNoEncontradaException("No se encontro el registro a eliminar.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("No se puede eliminar porque el registro tiene datos relacionados.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar en la base de datos: " + e.getMessage(), e);
        }
    }
}
