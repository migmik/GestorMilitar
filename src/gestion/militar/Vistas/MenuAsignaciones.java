package gestion.militar.Vistas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gestion.militar.Controladores.AsignacionesControlador;
import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Asignacion;

public class MenuAsignaciones extends MenuBase {
    AsignacionesControlador asignacionesControlador;

    public MenuAsignaciones(Scanner scanner, AsignacionesControlador asignacionesControlador) {
        super(scanner);
        this.asignacionesControlador = asignacionesControlador;
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestión de Asignaciones ---");
            System.out.println("1. Ingresar asignación");
            System.out.println("2. Modificar asignación");
            System.out.println("3. Consultar asignación por código de oficial");
            System.out.println("4. Listar todas las asignaciones");
            System.out.println("5. Eliminar asignación");
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
        int codigoOficial = leerEntero("Ingrese codigo del oficial: ");
        int codigoCuartel = leerEntero("Ingrese codigo del cuartel: ");

        try {
            asignacionesControlador.ingresar(codigoOficial, codigoCuartel);
            System.out.println("Asignación realizada con exito.");
        } catch (EntidadNoEncontradaException | EntidadDuplicadaException e) {
            System.out.println("No se pudo asignar: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigoOficial = leerEntero("Ingrese codigo del oficial para modificar su asignación: ");
        int codigoCuartel = leerEntero("Ingrese codigo del nuevo cuartel: ");

        try {
            asignacionesControlador.modificar(codigoOficial, codigoCuartel);
            System.out.println("Asignación modificada con exito.");
        } catch (EntidadNoEncontradaException | EntidadDuplicadaException e) {
            System.out.println("No se pudo modificar: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo del oficial para consultar la asignación: ");

        try {
            Asignacion asignacion = asignacionesControlador.consultarPorCodigoOficial(codigo);
            System.out.println("Asignación encontrada:\n " + asignacion.toString());
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void listar() {
        List<Asignacion> asignaciones = new ArrayList<>();
        try {
            asignaciones = asignacionesControlador.listarTodos();
            System.out.println("Lista de asignaciones:\n");
            asignaciones.forEach(System.out::println);
            if (asignaciones.isEmpty())
                System.out.println("No hay asignaciones registradas.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void eliminar() {
        int codigo = leerEntero("Ingrese codigo del oficial para eliminar la asignación: ");

        try {
            asignacionesControlador.eliminar(codigo);
            System.out.println("Asignación eliminada con exito.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }
}
