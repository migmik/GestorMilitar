package gestion.militar.Vistas;

import java.util.Scanner;

import gestion.militar.Enums.CamposPersonaEnum;
import gestion.militar.Excepciones.PersistenciaException;

public abstract class MenuPersonaBase extends MenuBase {

    public MenuPersonaBase(Scanner scanner) {
        super(scanner);
    }

    protected abstract void ejecutarActualizacion(int codigo, CamposPersonaEnum campo, String nuevoValor);

    // Metodo que reutilizan los menus de personas
    protected void gestionarEdicionPersona(int codigo) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nQue dato desea modificar?");
            CamposPersonaEnum[] campos = CamposPersonaEnum.values();
            for (int i = 0; i < campos.length; i++) {
                System.out.println((i + 1) + ". " + campos[i].getDescripcion());
            }
            System.out.println("0. Volver al menu anterior");
            int opcion = leerOpcion("Seleccione una opcion: ");

            if (opcion == 0) {
                salir = true;
            } else if (opcion >= 1 && opcion <= campos.length) {
                CamposPersonaEnum campoSeleccionado = campos[opcion - 1];
                String nuevoValor = leerTexto(
                        "Ingrese el nuevo valor para " + campoSeleccionado.getDescripcion() + ": ");
                try {
                    // orden al controlador a por método abstracto
                    ejecutarActualizacion(codigo, campoSeleccionado, nuevoValor);
                    System.out.println("Campo actualizado con exito.");
                } catch (PersistenciaException e) {
                    System.out.println("Error de base de datos: " + e.getMessage());
                } catch (RuntimeException e) {
                    System.out.println("No se pudo actualizar: " + e.getMessage());
                }
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }
}
