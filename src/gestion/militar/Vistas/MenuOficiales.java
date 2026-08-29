package gestion.militar.Vistas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gestion.militar.Controladores.OficialesControlador;
import gestion.militar.Enums.CamposPersonaEnum;
import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Oficial;

public class MenuOficiales extends MenuPersonaBase {

    OficialesControlador oficialesControlador;

    public MenuOficiales(Scanner scanner, OficialesControlador oficialesControlador) {
        super(scanner);
        this.oficialesControlador = oficialesControlador;
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestión de Oficiales ---");
            System.out.println("1. Ingresar oficial");
            System.out.println("2. Modificar oficial");
            System.out.println("3. Consultar oficial por código");
            System.out.println("4. Listar todos los oficiales");
            System.out.println("5. Eliminar oficial");
            System.out.println("0. Volver");
            switch (leerOpcion("Ingrese opción: ")) {
                case 1:
                    ingresar();
                    break;
                case 2:
                    modificar();
                    break;
                case 3:
                    consultar();
                    break;
                case 4:
                    listar();
                    break;
                case 5:
                    eliminar();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Volviendo...");
                    break;
                default:
                    break;
            }
        }
    }

    private void ingresar() {
        String dni = leerTexto("Ingrese DNI del oficial: ");
        String apellido = leerTexto("Ingrese apellido del oficial: ");
        String nombre = leerTexto("Ingrese nombre del oficial: ");
        try {
            oficialesControlador.ingresar(dni, apellido, nombre);
            System.out.println("Oficial registrado con exito!");
        } catch (EntidadDuplicadaException | IllegalArgumentException e) {
            System.out.println("No se pudo registrar: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigo = leerEntero("Ingrese codigo de oficial a modificar: ");
        try {
            Oficial oficialExistente = oficialesControlador.consultarPorCodigo(codigo);
            System.out.println("Modificacion del oficial: " + oficialExistente.mostrarInfo());
            gestionarEdicionPersona(codigo);

        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo de oficial: ");

        try {
            Oficial oficial = oficialesControlador.consultarPorCodigo(codigo);
            System.out.println("Oficial encontrado: \n" + oficial.mostrarInfo());
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void listar() {
        List<Oficial> oficiales = new ArrayList<>();
        try {
            oficiales = oficialesControlador.listarTodos();
            System.out.println("Lista de oficiales:\n");
            oficiales.forEach(System.out::println);
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void eliminar() {
        int codigo = leerEntero("Ingrese codigo de oficial a eliminar: ");
        try {
            oficialesControlador.eliminar(codigo);
            System.out.println("Oficial eliminado con exito.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    @Override
    protected void ejecutarActualización(int codigo, CamposPersonaEnum campo, String nuevoValor) {
        oficialesControlador.modificar(codigo, campo, nuevoValor);
    }
}
