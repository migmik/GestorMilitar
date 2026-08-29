package gestion.militar.Vistas;

import java.util.Scanner;

import gestion.militar.Enums.CamposPersonaEnum;

public abstract class MenuPersonaBase extends MenuBase {

    public MenuPersonaBase(Scanner scanner) {
        super(scanner);
    }

    protected abstract void ejecutarActualización(int codigo, CamposPersonaEnum campo, String nuevoValor);

    // Metodo que reutilizan los menús de personas
    protected void gestionarEdicionPersona(int codigo) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n¿Qué dato desea modificar?");
            CamposPersonaEnum[] campos = CamposPersonaEnum.values();
            for (int i = 0; i < campos.length; i++) {
                System.out.println((i + 1) + ". " + campos[i].getDescripcion());
            }
            System.out.println("0. Volver al menú anterior");
            int opcion = leerOpcion("Seleccione una opción: ");

            if (opcion == 0) {
                salir = true;
            } else if (opcion >= 1 && opcion <= campos.length) {
                CamposPersonaEnum campoSeleccionado = campos[opcion - 1];
                String nuevoValor = leerTexto(
                        "Ingrese el nuevo valor para " + campoSeleccionado.getDescripcion() + ": ");
                try {
                    // orden al controlador a por método abstracto
                    ejecutarActualización(codigo, campoSeleccionado, nuevoValor);
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