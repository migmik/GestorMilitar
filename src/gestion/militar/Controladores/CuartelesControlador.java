package gestion.militar.Controladores;

import java.util.List;
import gestion.militar.DAOS.GenericoDAO;
import gestion.militar.Enums.CamposCuartelEnum;
import gestion.militar.Excepciones.CapacidadExcedidaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Cuartel;
import gestion.militar.Repositorios.ReservaRepositorio;

public class CuartelesControlador {
    private final GenericoDAO<Cuartel, Integer> cuartelDAO;
    private final ReservaRepositorio reservaRepositorio;

    public CuartelesControlador(GenericoDAO<Cuartel, Integer> cuartelDAO, ReservaRepositorio reservaRepositorio) {
        this.cuartelDAO = cuartelDAO;
        this.reservaRepositorio = reservaRepositorio;
    }

    public void ingresar(String nombre, int capacidad) {
        Cuartel cuartel = new Cuartel(nombre, capacidad);
        cuartelDAO.crear(cuartel);
    }

    public void modificar(int codigo, CamposCuartelEnum campo, String nuevoValor) {
        Cuartel cuartel = consultarPorCodigo(codigo);
        switch (campo) {
            case NOMBRE:
                cuartel.setNombre(nuevoValor);
                break;
            case CAPACIDAD:
                int nuevaCapacidad = Integer.parseInt(nuevoValor);
                int cantidadReservas = reservaRepositorio.contarReservasPorCuartel(codigo);
                if (nuevaCapacidad < cantidadReservas) {
                    throw new CapacidadExcedidaException(
                            "La capacidad no puede ser menor a la cantidad de soldados reservados actualmente");
                }
                cuartel.setCapacidad(nuevaCapacidad);
                break;
        }
        cuartelDAO.actualizar(cuartel);
    }

    public Cuartel consultarPorCodigo(int codigo) {
        return cuartelDAO.encontrarPorID(codigo).orElseThrow(
                () -> new EntidadNoEncontradaException("No se encontró ningún cuartel con el código " + codigo));
    }

    public List<Cuartel> listarTodos() {
        return cuartelDAO.encontrarTodos();
    }

    public void eliminar(int codigo) {
        cuartelDAO.eliminar(codigo);
    }
}
