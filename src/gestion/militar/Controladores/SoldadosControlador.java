package gestion.militar.Controladores;

import java.util.List;

import gestion.militar.DAOS.SoldadoDAO;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Soldado;

public class SoldadosControlador {

    private final SoldadoDAO soldadoDAO;

    public SoldadosControlador(SoldadoDAO soldadoDAO) {
        this.soldadoDAO = soldadoDAO;
    }

    public void ingresar(String dni, String apellido, String nombre) {
        Soldado soldado = new Soldado(dni, apellido, nombre);
        soldadoDAO.crear(soldado);
    }

    public void modificar(int codigo, String nuevoDNI, String nuevoNombre, String nuevoApellido) {
        Soldado soldado = new Soldado(codigo, nuevoDNI, nuevoApellido, nuevoNombre);
        soldadoDAO.actualizar(soldado);
    }

    public Soldado consultarPorCodigo(int codigo) {
        return soldadoDAO.encontrarPorID(codigo).orElseThrow(
                () -> new EntidadNoEncontradaException("No se encontró ningún soldado con el código " + codigo));
    }

    public List<Soldado> listarTodos() {
        return soldadoDAO.encontrarTodos();
    }

    public void eliminar(int codigo) {
        soldadoDAO.eliminar(codigo);
    }
}