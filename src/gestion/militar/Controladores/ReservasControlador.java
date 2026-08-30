package gestion.militar.Controladores;

import java.util.List;
import gestion.militar.Excepciones.CapacidadExcedidaException;
import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Cuartel;
import gestion.militar.Modelos.Reserva;
import gestion.militar.Modelos.Soldado;
import gestion.militar.Repositorios.ReservaRepositorio;

public class ReservasControlador {
    private final ReservaRepositorio reservaRepositorio;
    private final SoldadosControlador soldadosControlador;
    private final CuartelesControlador cuartelesControlador;

    public ReservasControlador(ReservaRepositorio reservaRepositorio, SoldadosControlador soldadosControlador,
            CuartelesControlador cuartelesControlador) {
        this.reservaRepositorio = reservaRepositorio;
        this.soldadosControlador = soldadosControlador;
        this.cuartelesControlador = cuartelesControlador;
    }

    public void ingresar(int codigoSoldado, int codigoCuartel) {
        Soldado soldado = soldadosControlador.consultarPorCodigo(codigoSoldado);
        Cuartel cuartel = cuartelesControlador.consultarPorCodigo(codigoCuartel);
        if (soldadoTieneUnaReservaActiva(codigoSoldado)) {
            throw new EntidadDuplicadaException("El soldado ya posee una reserva.");
        }
        if (!hayCapacidadEnCuartel(cuartel)) {
            throw new CapacidadExcedidaException("El cuartel no posee capacidad de alojamiento.");
        }
        Reserva reserva = new Reserva(soldado, cuartel);
        reservaRepositorio.crear(reserva);
    }

    public void modificar(int codigoSoldado, int codigoNuevoCuartel) {
        Soldado soldado = soldadosControlador.consultarPorCodigo(codigoSoldado);
        Cuartel nuevoCuartel = cuartelesControlador.consultarPorCodigo(codigoNuevoCuartel);
        Reserva reservaActual = reservaRepositorio.encontrarPorID(codigoSoldado).orElseThrow(
                () -> new EntidadNoEncontradaException(
                        "No se encontro ninguna reserva asociada al codigo " + codigoSoldado));
        if (reservaActual.getCuartel().getCodigo() == codigoNuevoCuartel) {
            throw new EntidadDuplicadaException("El soldado ya posee una reserva en ese cuartel.");
        }
        if (!hayCapacidadEnCuartel(nuevoCuartel)) {
            throw new CapacidadExcedidaException("El cuartel no posee capacidad de alojamiento.");
        }
        Reserva reservaModificada = new Reserva(soldado, nuevoCuartel);
        reservaRepositorio.actualizar(reservaModificada);
    }

    public Reserva consultarPorCodigoSoldado(int codigoSoldado) {
        return reservaRepositorio.encontrarPorID(codigoSoldado).orElseThrow(
                () -> new EntidadNoEncontradaException("No se encontro ninguna reserva para el " +
                        codigoSoldado));

    }

    public List<Reserva> listarTodos() {
        return reservaRepositorio.encontrarTodos();
    }

    public void eliminar(int codigoSoldado) {
        reservaRepositorio.eliminar(codigoSoldado);
    }

    public List<Reserva> listarReservasPorCuartel(int codigoCuartel) {
        cuartelesControlador.consultarPorCodigo(codigoCuartel);
        return reservaRepositorio.listarReservasPorCuartel(codigoCuartel);
    }

    public boolean hayCapacidadEnCuartel(Cuartel cuartel) {
        int cantidadReservas = reservaRepositorio.contarReservasPorCuartel(cuartel.getCodigo());
        return cantidadReservas < cuartel.getCapacidad();
    }

    public boolean soldadoTieneUnaReservaActiva(int codigoSoldado) {
        return reservaRepositorio.encontrarPorID(codigoSoldado).isPresent();
    }
}
