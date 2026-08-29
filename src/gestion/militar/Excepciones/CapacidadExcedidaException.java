package gestion.militar.Excepciones;

public class CapacidadExcedidaException extends RuntimeException {
    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}