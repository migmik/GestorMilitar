package gestion.militar.Controladores;

import java.util.List;

import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Asignacion;
import gestion.militar.Modelos.Cuartel;
import gestion.militar.Modelos.Oficial;
import gestion.militar.Repositorios.AsignacionRepositorio;

public class AsignacionesControlador {
    private final AsignacionRepositorio asignacionRepositorio;
    private final OficialesControlador oficialesControlador;
    private final CuartelesControlador cuartelesControlador;

    public AsignacionesControlador(AsignacionRepositorio asignacionRepositorio,
            OficialesControlador oficialesControlador,
            CuartelesControlador cuartelesControlador) {
        this.asignacionRepositorio = asignacionRepositorio;
        this.oficialesControlador = oficialesControlador;
        this.cuartelesControlador = cuartelesControlador;
    }

    public void ingresar(int codigoOficial, int codigoCuartel) {
        Oficial oficial = oficialesControlador.consultarPorCodigo(codigoOficial);
        Cuartel cuartel = cuartelesControlador.consultarPorCodigo(codigoCuartel);
        if (oficialTieneUnaAsignacionActiva(codigoOficial)) {
            throw new EntidadDuplicadaException("El oficial ya posee una asignación.");
        }
        if (cuartelTieneOficialAsignado(cuartel)) {
            throw new EntidadDuplicadaException("El cuartel ya tiene un oficial asignado.");
        }
        Asignacion asignacion = new Asignacion(oficial, cuartel);
        asignacionRepositorio.crear(asignacion);
    }

    public void modificar(int codigoOficial, int codigoNuevoCuartel) {
        Oficial oficial = oficialesControlador.consultarPorCodigo(codigoOficial);
        Cuartel nuevoCuartel = cuartelesControlador.consultarPorCodigo(codigoNuevoCuartel);
        Asignacion asignacionActual = asignacionRepositorio.encontrarPorID(codigoOficial).orElseThrow(
                () -> new EntidadNoEncontradaException(
                        "No se encontró ninguna asignación asociada al codigo " + codigoOficial));
        if (asignacionActual.getCuartel().getCodigo() == codigoNuevoCuartel) {
            throw new EntidadDuplicadaException("El oficial ya posee una asignación en ese cuartel.");
        }
        if (cuartelTieneOficialAsignado(nuevoCuartel)) {
            throw new EntidadDuplicadaException("El cuartel ya tiene un oficial asignado.");
        }
        Asignacion asignacionModificada = new Asignacion(oficial, nuevoCuartel);
        asignacionRepositorio.actualizar(asignacionModificada);
    }

    public Asignacion consultarPorCodigoOficial(int codigoOficial) {
        return asignacionRepositorio.encontrarPorID(codigoOficial).orElseThrow(
                () -> new EntidadNoEncontradaException("No se encontró ninguna asignación para el " +
                        codigoOficial));
    }

    public List<Asignacion> listarTodos() {
        return asignacionRepositorio.encontrarTodos();
    }

    public void eliminar(int codigoOficial) {
        asignacionRepositorio.eliminar(codigoOficial);
    }

    public Asignacion consultarAsignacionPorCodigoDeCuartel(int codigoCuartel) {
        return asignacionRepositorio.consultarAsignacionPorCodigoDeCuartel(codigoCuartel);
    }

    public boolean cuartelTieneOficialAsignado(Cuartel cuartel) {
        int cantidadAsignaciones = asignacionRepositorio.contarAsignacionesPorCuartel(cuartel.getCodigo());
        return cantidadAsignaciones > 0;
    }

    public boolean oficialTieneUnaAsignacionActiva(int codigoOficial) {
        return asignacionRepositorio.encontrarPorID(codigoOficial).isPresent();
    }

}
