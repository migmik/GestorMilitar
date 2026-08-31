package gestion.militar.Modelos;

public class Reserva {
    private Soldado soldado;
    private Cuartel cuartel;

    public Reserva(Soldado soldado, Cuartel cuartel) {
        setSoldado(soldado);
        setCuartel(cuartel);
    }

    public Soldado getSoldado() {
        return soldado;
    }

    public void setSoldado(Soldado soldado) {
        if (soldado == null) {
            throw new IllegalArgumentException("El soldado no puede ser nulo.");
        }
        this.soldado = soldado;
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
        return "Reserva | Soldado: " + soldado.getApellido() + ", " + soldado.getNombre()
                + " (Codigo: " + soldado.getCodigo() + ", DNI: " + soldado.getDni() + ")"
                + " | Cuartel: " + cuartel.getNombre()
                + " (Codigo: " + cuartel.getCodigo() + ", Capacidad: " + cuartel.getCapacidad() + ")";
    }

}
