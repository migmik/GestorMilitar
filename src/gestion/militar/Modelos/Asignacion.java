package gestion.militar.Modelos;

public class Asignacion {
    private Oficial oficial;
    private Cuartel cuartel;

    public Asignacion(Oficial oficial, Cuartel cuartel) {
        setOficial(oficial);
        setCuartel(cuartel);
    }

    public Oficial getOficial() {
        return oficial;
    }

    public void setOficial(Oficial oficial) {
        if (oficial == null) {
            throw new IllegalArgumentException("El oficial no puede ser nulo.");
        }
        this.oficial = oficial;
    }

    public Cuartel getCuartel() {
        return cuartel;
    }

    public void setCuartel(Cuartel cuartel) {
        if (cuartel == null) {
            throw new IllegalArgumentException("El cuartel no puede ser nulo.");
        }
        this.cuartel = cuartel;
    }

    @Override
    public String toString() {
        return "Asignacion | Oficial: " + oficial.getApellido() + ", " + oficial.getNombre()
                + " (Codigo: " + oficial.getCodigo() + ", DNI: " + oficial.getDni() + ")"
                + " | Cuartel: " + cuartel.getNombre()
                + " (Codigo: " + cuartel.getCodigo() + ", Capacidad: " + cuartel.getCapacidad() + ")";
    }

}
