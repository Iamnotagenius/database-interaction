package jabainitmo.database;

import java.sql.SQLException;
import java.util.List;

public interface HouseDbContext extends DbContext<House> {
    List<House> getAllByStreetId(long id) throws SQLException;
}
