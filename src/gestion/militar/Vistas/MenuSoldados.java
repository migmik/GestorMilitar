package gestion.militar.Vistas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gestion.militar.Controladores.SoldadosControlador;
import gestion.militar.Enums.CamposPersonaEnum;
import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Soldado;

public class MenuSoldados extends MenuPersonaBase {
    SoldadosControlador soldadosControlador;

    public MenuSoldados(Scanner scanner, SoldadosControlador soldadosControlador) {
        super(scanner);
        this.soldadosControlador = soldadosControlador;
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestión de Soldados ---");
            System.out.println("1. Ingresar soldado");
            System.out.println("2. Modificar soldado");
            System.out.println("3. Consultar soldado por código");
            System.out.println("4. Listar todos los soldados");
            System.out.println("5. Eliminar soldado");
            System.out.println("0. Volver");
            switch (leerOpcion("Ingrese Opción: ")) {
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
        String dni = leerTexto("Ingrese DNI del soldado: ");
        String apellido = leerTexto("Ingrese apellido del soldado: ");
        String nombre = leerTexto("Ingrese nombre del soldado: ");
        try {
            soldadosControlador.ingresar(dni, apellido, nombre);
            System.out.println("Soldado registrado con exito!");
        } catch (EntidadDuplicadaException | IllegalArgumentException e) {
            System.out.println("No se pudo registrar: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigo = leerEntero("Ingrese codigo de soldado a modificar: ");
        try {
            Soldado soldadoExistente = soldadosControlador.consultarPorCodigo(codigo);
            System.out.println("Modificacion del soldado: " + soldadoExistente.mostrarInfo());
            gestionarEdicionPersona(codigo);

        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo de soldado: ");

        try {
            Soldado soldado = soldadosControlador.consultarPorCodigo(codigo);
            System.out.println("Soldado encontrado: \n" + soldado.mostrarInfo());
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void listar() {
        List<Soldado> soldados = new ArrayList<>();
        try {
            soldados = soldadosControlador.listarTodos();
            System.out.println("Lista de soldado:\n");
            soldados.forEach(System.out::println);
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void eliminar() {
        int codigo = leerEntero("Ingrese codigo de soldado a eliminar: ");
        try {
            soldadosControlador.eliminar(codigo);
            System.out.println("Soldado eliminado con exito.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    @Override
    protected void ejecutarActualización(int codigo, CamposPersonaEnum campo, String nuevoValor) {
        soldadosControlador.modificar(codigo, campo, nuevoValor);
    }
}