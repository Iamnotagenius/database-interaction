package jabainitmo.database;

import java.sql.SQLException;
import java.util.List;

public interface DbContext<T> {
    void save(T entity) throws SQLException;

    void deleteById(long id) throws SQLException;

    void deleteAll() throws SQLException;

    T update(T entity) throws SQLException;

    T getById(long id) throws SQLException;

    List<T> getAll() throws SQLException;
}
