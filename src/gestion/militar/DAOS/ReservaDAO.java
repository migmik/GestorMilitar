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
import gestion.militar.Modelos.Cuartel;
import gestion.militar.Modelos.Reserva;
import gestion.militar.Modelos.Soldado;

public class ReservaDAO implements GenericoDAO<Reserva, Integer> {
    protected Connection conexion;

    public ReservaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void crear(Reserva reserva) {
        String consulta = "INSERT INTO reservas (soldados_codigo, cuarteles_codigo) VALUES (?, ?)";
        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, reserva.getSoldado().getCodigo());
            sentencia.setInt(2, reserva.getCuartel().getCodigo());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new EntidadDuplicadaException(
                        "El soldado ID " + reserva.getSoldado().getCodigo() + " ya posee una reserva activa.");
            }
            throw new RuntimeException("Error técnico en la base de datos:" + e.getMessage());
        }
    }

    @Override
    public Optional<Reserva> encontrarPorID(Integer codigoSoldado) {
        String consulta = "SELECT r.soldados_codigo, r.cuarteles_codigo, " +
                "s.nombre AS s_nombre, s.apellido AS s_apellido, s.dni AS s_dni, " +
                "c.nombre AS c_nombre, c.capacidad " +
                "FROM reservas r " +
                "INNER JOIN soldados s ON r.soldados_codigo = s.codigo " +
                "INNER JOIN cuarteles c ON r.cuarteles_codigo = c.codigo " +
                "WHERE r.soldados_codigo = ?";
        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigoSoldado);
            try (ResultSet resultSet = sentencia.executeQuery()) {
                if (resultSet.next()) {
                    Soldado soldado = new Soldado(resultSet.getString("s_dni"), resultSet.getString("s_apellido"),
                            resultSet.getString("s_nombre"));
                    soldado.setCodigo(resultSet.getInt("soldados_codigo"));

                    Cuartel cuartel = new Cuartel(resultSet.getInt("cuarteles_codigo"), resultSet.getString("c_nombre"),
                            resultSet.getInt("capacidad"));
                    return Optional.of(new Reserva(soldado, cuartel));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error técnico en la base de datos:" + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Reserva> encontrarTodos() {
        List<Reserva> lista = new ArrayList<>();
        String consulta = "SELECT r.soldados_codigo, r.cuarteles_codigo, " +
                "s.nombre AS s_nombre, s.apellido AS s_apellido, s.dni AS s_dni, " +
                "c.nombre AS c_nombre, c.capacidad " +
                "FROM reservas r " +
                "INNER JOIN soldados s ON r.soldados_codigo = s.codigo " +
                "INNER JOIN cuarteles c ON r.cuarteles_codigo = c.codigo";

        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                Soldado soldado = new Soldado(resultSet.getInt("soldados_codigo"), resultSet.getString("s_dni"),
                        resultSet.getString("s_apellido"), resultSet.getString("s_nombre"));
                Cuartel cuartel = new Cuartel(
                        resultSet.getInt("cuarteles_codigo"),
                        resultSet.getString("c_nombre"),
                        resultSet.getInt("capacidad"));
                lista.add(new Reserva(soldado, cuartel));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las reservas de la base de datos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Reserva entidad) {
        String consulta = "UPDATE reservas SET cuarteles_codigo = ? WHERE soldados_codigo = ?";
        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, entidad.getCuartel().getCodigo());
            sentencia.setInt(2, entidad.getSoldado().getCodigo());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la reserva en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer codigo) {
        String consulta = "DELETE FROM reservas WHERE soldados_codigo = ?";
        try (Connection conexion = ConfiguracionBD.conexion();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigo);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la reserva de la base de datos: " + e.getMessage());
        }
    }

    public String getTabla() {
        return "reservas";
    }
}
