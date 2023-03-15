package jabainitmo.database.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jabainitmo.database.Street;

public class JdbcStreetContext extends JdbcDbContext<Street> {
    @Override
    protected Street fromColumns(ResultSet set, Connection connection) throws SQLException {
        return new Street(set.getLong("id"), set.getString("name"), set.getInt("post_index"));
    }

    @Override
    protected void populateStatement(PreparedStatement statement, Street entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getPostIndex());
    }

    @Override
    protected String getTableName() {
        return "streets";
    }

    @Override
    protected List<String> getColumns() {
        return List.of("name", "post_index");
    }
}
