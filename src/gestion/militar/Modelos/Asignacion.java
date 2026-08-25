package gestion.militar.Modelos;

public class Asignacion {
    private Oficial oficial;
    private Cuartel cuartel;

    public Asignacion(Oficial oficial, Cuartel cuartel) {
        this.oficial = oficial;
        this.cuartel = cuartel;
    }

    public Oficial getOficial() {
        return oficial;
    }

    public void setOficial(Oficial oficial) {
        this.oficial = oficial;
    }

    public Cuartel getCuartel() {
        return cuartel;
    }

    public void setCuartel(Cuartel cuartel) {
        this.cuartel = cuartel;
    }

    @Override
    public String toString() {
        return "Asignacion [oficial=" + oficial + ", cuartel=" + cuartel + "]";
    }

}
