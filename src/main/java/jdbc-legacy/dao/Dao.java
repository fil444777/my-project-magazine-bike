package jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao <K, E> {

    boolean update(E motorcycles);
    List<E> findAll();
    Optional<E> findById(K id);
    E save(E motorcycles);
    boolean delete(K id);
}
