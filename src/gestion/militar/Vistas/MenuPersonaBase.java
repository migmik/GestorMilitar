package gestion.militar.Vistas;

import java.util.Scanner;

import gestion.militar.Modelos.Persona;

public abstract class MenuPersonaBase extends MenuBase {

    public MenuPersonaBase(Scanner scanner) {
        super(scanner);
    }

    protected abstract void ejecutarActualización(int codigo, int opcionAEditar, String nuevoValor);

    // Metodo que reutilizan los menús de personas
    protected void gestionarEdicionPersona(int codigo) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n¿Qué dato desea modificar?");
            System.out.println("1. DNI");
            System.out.println("2. Nombre");
            System.out.println("3. Apellido");
            System.out.println("0. Volver al menú anterior");

            int opcion = leerOpcion("Seleccione una opción: ");

            if (opcion == 0) {
                salir = true;
            } else if (opcion >= 1 && opcion <= 3) {
                String nuevoValor = leerTexto("Ingrese el nuevo valor: ");
                try {
                    // orden al controlador a por método abstracto
                    ejecutarActualización(codigo, opcion, nuevoValor);
                    System.out.println("Campo actualizado con éxito.");
                } catch (RuntimeException e) {
                    System.out.println("No se pudo actualizar: " + e.getMessage());
                }
            } else {
                System.out.println("Opcion inválida");
            }
        }
    }
}