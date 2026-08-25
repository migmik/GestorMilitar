package gestion.militar.DAOS;

import java.util.List;
import java.util.Optional;

interface GenericoDAO<T, ID> {
    void crear(T entidad);

    List<T> encontrarTodos();

    Optional<T> encontrarPorID(ID codigo);

    void actualizar(T entidad);

    void eliminar(ID codigo);

}
