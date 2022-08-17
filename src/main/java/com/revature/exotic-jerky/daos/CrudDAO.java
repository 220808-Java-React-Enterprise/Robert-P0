package daos;

import java.io.IOException;
import java.util.List;

public interface CrudDAO<T> {
    void save(T obj) throws IOException;
    void update(T obj);
    void delete(String id);
    void getById(String id);
    List<T> getAll();
}
