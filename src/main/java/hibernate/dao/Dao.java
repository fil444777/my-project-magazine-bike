package hibernate.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E>{

    boolean update (E user);
    List<E> findAll ();
    Optional<E> findById (K id);
    E save(E user);
    boolean delete(K id);


}
