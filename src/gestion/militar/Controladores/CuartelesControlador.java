package gestion.militar.Controladores;

import java.util.List;
import gestion.militar.Enums.CamposCuartelEnum;
import gestion.militar.Excepciones.CapacidadExcedidaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Cuartel;
import gestion.militar.Repositorios.CuartelRepositorio;
import gestion.militar.Repositorios.ReservaRepositorio;

public class CuartelesControlador {
    private final CuartelRepositorio cuartelRepositorio;
    private final ReservaRepositorio reservaRepositorio;

    public CuartelesControlador(CuartelRepositorio cuartelRepositorio, ReservaRepositorio reservaRepositorio) {
        this.cuartelRepositorio = cuartelRepositorio;
        this.reservaRepositorio = reservaRepositorio;
    }

    public void ingresar(String nombre, int capacidad) {
        Cuartel cuartel = new Cuartel(nombre, capacidad);
        cuartelRepositorio.crear(cuartel);
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
        cuartelRepositorio.actualizar(cuartel);
    }

    public Cuartel consultarPorCodigo(int codigo) {
        return cuartelRepositorio.encontrarPorID(codigo).orElseThrow(
                () -> new EntidadNoEncontradaException("No se encontro ningun cuartel con el codigo " + codigo));
    }

    public List<Cuartel> listarTodos() {
        return cuartelRepositorio.encontrarTodos();
    }

    public void eliminar(int codigo) {
        cuartelRepositorio.eliminar(codigo);
    }
}
