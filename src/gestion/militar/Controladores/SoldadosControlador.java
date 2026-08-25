package gestion.militar.Controladores;

import java.util.List;

import gestion.militar.DAOS.SoldadoDAO;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Soldado;
import gestion.militar.Vistas.CamposPersonaEnum;

public class SoldadosControlador {

    private final SoldadoDAO soldadoDAO;

    public SoldadosControlador(SoldadoDAO soldadoDAO) {
        this.soldadoDAO = soldadoDAO;
    }

    public void ingresar(String dni, String apellido, String nombre) {
        Soldado soldado = new Soldado(dni, apellido, nombre);
        soldadoDAO.crear(soldado);
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