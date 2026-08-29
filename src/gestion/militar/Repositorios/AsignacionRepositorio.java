package gestion.militar.Repositorios;

import gestion.militar.DAOS.GenericoDAO;
import gestion.militar.Modelos.Asignacion;

public interface AsignacionRepositorio extends GenericoDAO<Asignacion, Integer> {
    int contarAsignacionesPorCuartel(int codigoCuartel);

    Asignacion consultarAsignacionPorCodigoDeCuartel(int codigoCuartel);
}