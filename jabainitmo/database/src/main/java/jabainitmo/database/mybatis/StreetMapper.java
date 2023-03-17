package jabainitmo.database.mybatis;

import java.util.List;

import jabainitmo.database.Street;

public interface StreetMapper {
    int saveStreet(Street street);

    int deleteStreet(long id);

    int deleteAllStreets();

    int updateStreet(Street street);

    Street selectStreet(long id);

    List<Street> selectAllStreets();
}
