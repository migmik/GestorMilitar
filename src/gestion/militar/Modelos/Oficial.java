package gestion.militar.Modelos;

public class Oficial extends Persona {

    public Oficial(String dni, String apellido, String nombre) {
        super(dni, apellido, nombre);
    }

    public Oficial(int codigo, String dni, String apellido, String nombre) {
        super(codigo, dni, apellido, nombre);
    }

    @Override
    public String toString() {
        return mostrarInfo();
    }

    @Override
    public String mostrarInfo() {
        return "Oficial | " + super.toString();
    }
}
