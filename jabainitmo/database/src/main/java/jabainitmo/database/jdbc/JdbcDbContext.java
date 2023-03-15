package jabainitmo.database.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jabainitmo.database.DbContext;
import jabainitmo.database.Entity;

public abstract class JdbcDbContext<T extends Entity> implements DbContext<T> {
    protected static String ConnURL = "jdbc:postgresql://172.17.0.2/forjava?user=postgres&password=123654";

    public void save(T entity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnURL);
                PreparedStatement statement = prepareStatement(connection,
                        "INSERT INTO [table] VALUES (DEFAULT, %s)",
                        String.join(", ", "?".repeat(getColumns().size()).split("")))) {
            populateStatement(statement, entity);
            statement.executeUpdate();
        }
    }

    public void deleteById(long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnURL);
                PreparedStatement statement = prepareStatement(connection, "DELETE FROM [table] WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public void deleteAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnURL);
                PreparedStatement statement = prepareStatement(connection, "TRUNCATE TABLE [table]")) {
            statement.executeUpdate();
        }
    }

    public T update(T entity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnURL);
                PreparedStatement statement = prepareStatement(connection, "UPDATE [table] SET %s WHERE id = ?",
                        getColumns().stream().map(col -> col.concat(" = ?")).collect(Collectors.joining(", ")))) {
            populateStatement(statement, entity);
            statement.setLong(getColumns().size() + 1, entity.getId());
            statement.executeUpdate();
            return entity;
        }
    }

    public T getById(long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnURL)) {
            return getById(connection, id);
        }
    }

    public List<T> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnURL);
                PreparedStatement statement = prepareStatement(connection, "SELECT * FROM [table]")) {
            var set = statement.executeQuery();
            var list = new ArrayList<T>();
            while (set.next()) {
                list.add(fromColumns(set, connection));
            }
            return list;
        }
    }

    public T getById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = prepareStatement(connection, "SELECT * FROM [table] WHERE id = ?")) {
            statement.setLong(1, id);
            var set = statement.executeQuery();
            set.next();
            if (set.isAfterLast()) {
                throw new RuntimeException("House not found");
            }
            return fromColumns(set, connection);
        }
    }

    protected abstract List<String> getColumns();

    protected abstract T fromColumns(ResultSet set, Connection connection) throws SQLException;

    protected abstract void populateStatement(PreparedStatement statement, T entity) throws SQLException;

    protected abstract String getTableName();

    protected PreparedStatement prepareStatement(Connection connection, String formattedStatement, Object... args)
            throws SQLException {
        return connection.prepareStatement(String.format(formattedStatement.replace("[table]", getTableName()), args));
    }
}
