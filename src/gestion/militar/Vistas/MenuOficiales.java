package gestion.militar.Vistas;

import java.util.Scanner;

public class MenuOficiales extends MenuPersonaBase {

    public MenuOficiales(Scanner scanner) {
        super(scanner);
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
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigo = leerEntero("Ingrese codigo de oficial a modificar: ");
        String dni = leerTexto("Ingrese nuevo DNI: ");
        String apellido = leerTexto("Ingrese nuevo apellido: ");
        String nombre = leerTexto("Ingrese nuevo nombre: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo de oficial: ");

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
        int codigo = leerEntero("Ingrese codigo de oficial a eliminar: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void ejecutarActualización(int codigo, CamposPersonaEnum campo, String nuevoValor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ejecutarActualización'");
    }
}