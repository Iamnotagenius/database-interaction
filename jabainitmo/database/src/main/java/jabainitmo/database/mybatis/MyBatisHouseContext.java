package jabainitmo.database.mybatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import jabainitmo.database.House;
import jabainitmo.database.HouseDbContext;

public class MyBatisHouseContext implements HouseDbContext {
    private SqlSessionFactory sessionFactory;

    public MyBatisHouseContext() throws IOException {
        sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("META-INF/mybatis-config.xml"));
    }

    @Override
    public void save(House entity) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            HouseMapper mapper = session.getMapper(HouseMapper.class);
            mapper.saveHouse(entity);
            session.commit();
        }
    }

    @Override
    public void deleteById(long id) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            HouseMapper mapper = session.getMapper(HouseMapper.class);
            mapper.deleteHouse(id);
            session.commit();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            HouseMapper mapper = session.getMapper(HouseMapper.class);
            mapper.deleteAllHouses();
            session.commit();
        }
    }

    @Override
    public House update(House entity) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            HouseMapper mapper = session.getMapper(HouseMapper.class);
            mapper.updateHouse(entity);
            session.commit();
        }
        return entity;
    }

    @Override
    public House getById(long id) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            HouseMapper mapper = session.getMapper(HouseMapper.class);
            return mapper.selectHouse(id);
        }
    }

    @Override
    public List<House> getAll() throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            HouseMapper mapper = session.getMapper(HouseMapper.class);
            return mapper.selectAllHouses();
        }
    }

    @Override
    public List<House> getAllByStreetId(long id) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            HouseMapper mapper = session.getMapper(HouseMapper.class);
            return mapper.selectAllHousesOnStreet(id);
        }
    }
}
