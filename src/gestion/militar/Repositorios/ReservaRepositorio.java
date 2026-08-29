package gestion.militar.Repositorios;

import java.util.List;

import gestion.militar.DAOS.GenericoDAO;
import gestion.militar.Modelos.Reserva;

public interface ReservaRepositorio extends GenericoDAO<Reserva, Integer> {
    int contarReservasPorCuartel(int codigoCuartel);

    List<Reserva> listarReservasPorCuartel(int codigoCuartel);
}