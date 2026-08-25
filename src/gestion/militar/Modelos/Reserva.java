package gestion.militar.Modelos;

public class Reserva {
    private Soldado soldado;
    private Cuartel cuartel;

    public Reserva(Soldado soldado, Cuartel cuartel) {
        this.soldado = soldado;
        this.cuartel = cuartel;
    }

    public Soldado getSoldado() {
        return soldado;
    }

    public void setSoldado(Soldado soldado) {
        this.soldado = soldado;
    }

    public Cuartel getCuartel() {
        return cuartel;
    }

    public void setCuartel(Cuartel cuartel) {
        this.cuartel = cuartel;
    }

    @Override
    public String toString() {
        return "Reserva [soldado=" + soldado + ", cuartel=" + cuartel + "]";
    }

}
