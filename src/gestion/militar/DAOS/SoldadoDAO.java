package gestion.militar.DAOS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestion.militar.Modelos.Soldado;
import gestion.militar.Repositorios.SoldadoRepositorio;

public class SoldadoDAO extends PersonaDAO<Soldado> implements SoldadoRepositorio {

    public SoldadoDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    protected String getTabla() {
        return "soldados";
    }

    @Override
    protected Soldado crearDesdeResultSet(ResultSet resultSet)
            throws SQLException {
        return new Soldado(
                resultSet.getInt("codigo"),
                resultSet.getString("dni"),
                resultSet.getString("apellido"),
                resultSet.getString("nombre"));
    }
}
