package jabainitmo.database.mybatis;

import java.util.List;

import jabainitmo.database.House;

public interface HouseMapper {
    int saveHouse(House house);

    int deleteHouse(long id);

    int deleteAllHouses();

    int updateHouse(House house);

    House selectHouse(long id);

    List<House> selectAllHouses();

    List<House> selectAllHousesOnStreet(long id);
}
