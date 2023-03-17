package jabainitmo.database.mybatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import jabainitmo.database.DbContext;
import jabainitmo.database.Street;

public class MyBatisStreetContext implements DbContext<Street> {
    private SqlSessionFactory sessionFactory;

    public MyBatisStreetContext() throws IOException {
        sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("META-INF/mybatis-config.xml"));
    }

    @Override
    public void save(Street entity) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            StreetMapper mapper = session.getMapper(StreetMapper.class);
            mapper.saveStreet(entity);
            session.commit();
        }
    }

    @Override
    public void deleteById(long id) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            StreetMapper mapper = session.getMapper(StreetMapper.class);
            mapper.deleteStreet(id);
            session.commit();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            StreetMapper mapper = session.getMapper(StreetMapper.class);
            mapper.deleteAllStreets();
            session.commit();
        }
    }

    @Override
    public Street update(Street entity) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            StreetMapper mapper = session.getMapper(StreetMapper.class);
            mapper.updateStreet(entity);
            session.commit();
        }
        return entity;
    }

    @Override
    public Street getById(long id) throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            StreetMapper mapper = session.getMapper(StreetMapper.class);
            return mapper.selectStreet(id);
        }
    }

    @Override
    public List<Street> getAll() throws SQLException {
        try (SqlSession session = sessionFactory.openSession()) {
            StreetMapper mapper = session.getMapper(StreetMapper.class);
            return mapper.selectAllStreets();
        }
    }
}
