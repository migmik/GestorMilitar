package gestion.militar.Vistas;

import java.util.Scanner;

public class MenuAsignaciones extends MenuBase {

    public MenuAsignaciones(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestión de Asignaciones ---");
            System.out.println("1. Ingresar asignación");
            System.out.println("2. Modificar asignación");
            System.out.println("3. Consultar asignación por código");
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
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigo = leerEntero("Ingrese codigo de asignacion a modificar: ");
        int codigoOficial = leerEntero("Ingrese nuevo codigo de oficial: ");
        int codigoCuartel = leerEntero("Ingrese nuevo codigo de cuartel: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo de asignacion: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        int codigo = leerEntero("Ingrese codigo de asignacion a eliminar: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}