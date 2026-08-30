package gestion.militar.Controladores;

import java.util.List;

import gestion.militar.DAOS.GenericoDAO;
import gestion.militar.Enums.CamposPersonaEnum;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Oficial;

public class OficialesControlador {

    private final GenericoDAO<Oficial, Integer> oficialDAO;

    public OficialesControlador(GenericoDAO<Oficial, Integer> oficialDAO) {
        this.oficialDAO = oficialDAO;
    }

    public void ingresar(String dni, String apellido, String nombre) {
        Oficial oficial = new Oficial(dni, apellido, nombre);
        oficialDAO.crear(oficial);
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
        oficialDAO.actualizar(oficial);
    }

    public Oficial consultarPorCodigo(int codigo) {
        return oficialDAO.encontrarPorID(codigo).orElseThrow(
                () -> new EntidadNoEncontradaException("No se encontro ningun oficial con el codigo " + codigo));
    }

    public List<Oficial> listarTodos() {
        return oficialDAO.encontrarTodos();
    }

    public void eliminar(int codigo) {
        oficialDAO.eliminar(codigo);
    }
}
