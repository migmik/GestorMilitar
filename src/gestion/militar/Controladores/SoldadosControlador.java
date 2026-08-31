package gestion.militar.Controladores;

import java.util.List;

import gestion.militar.Enums.CamposPersonaEnum;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Soldado;
import gestion.militar.Repositorios.SoldadoRepositorio;

public class SoldadosControlador {

    private final SoldadoRepositorio soldadoRepositorio;

    public SoldadosControlador(SoldadoRepositorio soldadoRepositorio) {
        this.soldadoRepositorio = soldadoRepositorio;
    }

    public void ingresar(String dni, String apellido, String nombre) {
        Soldado soldado = new Soldado(dni, apellido, nombre);
        soldadoRepositorio.crear(soldado);
    }

    public void modificar(int codigo, CamposPersonaEnum campo, String nuevoValor) {
        Soldado soldado = consultarPorCodigo(codigo);
        switch (campo) {
            case DNI:
                soldado.setDni(nuevoValor);
                break;
            case NOMBRE:
                soldado.setNombre(nuevoValor);
                break;
            case APELLIDO:
                soldado.setApellido(nuevoValor);
                break;
        }
        soldadoRepositorio.actualizar(soldado);
    }

    public Soldado consultarPorCodigo(int codigo) {
        return soldadoRepositorio.encontrarPorID(codigo).orElseThrow(
                () -> new EntidadNoEncontradaException("No se encontro ningun soldado con el codigo " + codigo));
    }

    public List<Soldado> listarTodos() {
        return soldadoRepositorio.encontrarTodos();
    }

    public void eliminar(int codigo) {
        soldadoRepositorio.eliminar(codigo);
    }
}
