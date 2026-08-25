package gestion.militar.DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                } else {
                    throw new SQLException("No se pudo obtener el código del cuartel creado");
                }
            }

        } catch (Exception e) {
            System.out.println("Error al guardar el cuartel en MySQL: " + e.getMessage());
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

        } catch (Exception e) {
            System.out.println("Error al listar los cuarteles en MySQL: " + e.getMessage());
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

        } catch (Exception e) {
            System.out.println("Error al buscar el cuartel en MySQL: " + e.getMessage());
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
                throw new Exception("No se encontró el cuartel a modificar en MySQL");
            }

        } catch (Exception e) {
            System.out.println("Error al modificar el cuartel en MySQL: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer codigo) {
        String consulta = "DELETE FROM cuarteles WHERE codigo = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, codigo);
            sentencia.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al eliminar el cuartel en MySQL: " + e.getMessage());
        }
    }

    public String getTabla() {
        return "cuarteles";
    }
}