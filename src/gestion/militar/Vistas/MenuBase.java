package gestion.militar.Vistas;

import java.util.Scanner;

public abstract class MenuBase {
    protected final Scanner scanner;

    public MenuBase(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void mostrar();

    public int leerEntero(String mensaje) {
        int valor = -1;
        boolean esValido = false;

        while (!esValido) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor > 0) {
                    esValido = true;
                } else {
                    System.out.println("Número invalido.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
        return valor;
    }

    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        while (entrada.isEmpty()) {
            System.out.print("El campo no puede estar vacío. Intente de nuevo: ");
            entrada = scanner.nextLine().trim();
        }
        return entrada;
    }

    protected int leerOpcion(String mensaje) {
        int opcion = -1;
        boolean valida = false;

        while (!valida) {
            System.out.print(mensaje);
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                valida = true;
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Ingrese solo números.");
            }
        }
        return opcion;
    }
}
