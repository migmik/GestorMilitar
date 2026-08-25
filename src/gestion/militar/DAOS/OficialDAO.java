package gestion.militar.DAOS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestion.militar.Modelos.Oficial;

public class OficialDAO extends PersonaDAO<Oficial> {

    public OficialDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    protected String getTabla() {
        return "oficiales";
    }

    @Override
    protected Oficial crearDesdeResultSet(ResultSet resultSet)
            throws SQLException {
        return new Oficial(
                resultSet.getInt("codigo"),
                resultSet.getString("dni"),
                resultSet.getString("apellido"),
                resultSet.getString("nombre"));
    }
}