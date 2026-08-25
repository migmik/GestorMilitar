package gestion.militar.Vistas;

import java.util.Scanner;

public class MenuCuarteles extends MenuBase {

    public MenuCuarteles(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestión de Cuarteles ---");
            System.out.println("1. Ingresar cuartel");
            System.out.println("2. Modificar capacidad de cuartel");
            System.out.println("3. Consultar cuartel por código");
            System.out.println("4. Listar todos los cuarteles");
            System.out.println("5. Eliminar cuartel");
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
        String nombre = leerTexto("Ingrese nombre del cuartel: ");
        int capacidad = leerEntero("Ingrese capacidad del cuartel: ");
        try {

            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigo = leerEntero("Ingrese codigo de cuartel a modificar: ");
        String nombre = leerTexto("Ingrese nuevo nombre: ");
        int capacidad = leerEntero("Ingrese nueva capacidad: ");
        try {

            // llamada a servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo de cuartel: ");
        try {

            // llamada a servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        try {

            // llamada a service

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        int codigo = leerEntero("Ingrese codigo de cuartel a eliminar: ");
        try {
            // llamada a servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
