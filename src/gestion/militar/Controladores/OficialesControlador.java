package gestion.militar.Controladores;

import java.util.List;

import gestion.militar.Enums.CamposPersonaEnum;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Oficial;
import gestion.militar.Repositorios.OficialRepositorio;

public class OficialesControlador {

    private final OficialRepositorio oficialRepositorio;

    public OficialesControlador(OficialRepositorio oficialRepositorio) {
        this.oficialRepositorio = oficialRepositorio;
    }

    public void ingresar(String dni, String apellido, String nombre) {
        Oficial oficial = new Oficial(dni, apellido, nombre);
        oficialRepositorio.crear(oficial);
    }

    public void modificar(int codigo, CamposPersonaEnum campo, String nuevoValor) {
        Oficial oficial = consultarPorCodigo(codigo);
        switch (campo) {
            case DNI:
                oficial.setDni(nuevoValor);
                break;
            case NOMBRE:
                oficial.setNombre(nuevoValor);
                break;
            case APELLIDO:
                oficial.setApellido(nuevoValor);
                break;
        }
        oficialRepositorio.actualizar(oficial);
    }

    public Oficial consultarPorCodigo(int codigo) {
        return oficialRepositorio.encontrarPorID(codigo).orElseThrow(
                () -> new EntidadNoEncontradaException("No se encontro ningun oficial con el codigo " + codigo));
    }

    public List<Oficial> listarTodos() {
        return oficialRepositorio.encontrarTodos();
    }

    public void eliminar(int codigo) {
        oficialRepositorio.eliminar(codigo);
    }
}
