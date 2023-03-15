package jabainitmo.database.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jabainitmo.database.House;
import jabainitmo.database.HouseDbContext;
import jabainitmo.database.HouseType;

public class JdbcHouseContext extends JdbcDbContext<House> implements HouseDbContext {
    private JdbcStreetContext streetContext = new JdbcStreetContext();

    @Override
    protected House fromColumns(ResultSet set, Connection connection) throws SQLException {
        return new House(
                set.getLong("id"),
                set.getString("name"),
                set.getObject("date", LocalDate.class),
                set.getInt("floors"),
                Enum.valueOf(HouseType.class, set.getString("type")),
                streetContext.getById(connection, set.getLong("street")));
    }

    @Override
    protected void populateStatement(PreparedStatement statement, House entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setObject(2, entity.getDate());
        statement.setInt(3, entity.getFloors());
        statement.setString(4, entity.getType().name());
        statement.setLong(5, entity.getStreet().getId());
    }

    @Override
    protected String getTableName() {
        return "houses";
    }

    @Override
    public List<House> getAllByStreetId(long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnURL);
                PreparedStatement statement = prepareStatement(connection,
                        "SELECT * FROM [table] WHERE street = ?")) {
            statement.setLong(1, id);
            var set = statement.executeQuery();
            var list = new ArrayList<House>();
            while (set.next()) {
                list.add(fromColumns(set, connection));
            }
            return list;
        }
    }

    @Override
    protected List<String> getColumns() {
        return List.of("name", "date", "floors", "type", "street");
    }
}
