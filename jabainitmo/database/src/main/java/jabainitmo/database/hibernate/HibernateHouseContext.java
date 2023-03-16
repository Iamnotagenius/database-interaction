package jabainitmo.database.hibernate;

import java.sql.SQLException;
import java.util.List;

import jabainitmo.database.House;
import jabainitmo.database.HouseDbContext;
import jabainitmo.database.Street;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class HibernateHouseContext implements HouseDbContext {
    @PersistenceContext
    private EntityManager entityManager;

    public HibernateHouseContext() {
        super();
        entityManager = Persistence.createEntityManagerFactory("CRM").createEntityManager();
    }

    @Override
    public void save(House entity) throws SQLException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void deleteById(long id) throws SQLException {
        entityManager.remove(entityManager.getReference(House.class, id));
    }

    @Override
    public void deleteAll() throws SQLException {
        entityManager.createQuery("DELETE FROM House").executeUpdate();
    }

    @Override
    public House update(House entity) throws SQLException {
        return entity;
    }

    @Override
    public House getById(long id) throws SQLException {
        return entityManager.find(House.class, id);
    }

    @Override
    public List<House> getAll() throws SQLException {
        return entityManager.createQuery("SELECT h FROM House h", House.class).getResultList();
    }

    @Override
    public List<House> getAllByStreetId(long id) throws SQLException {
        var query = entityManager.createQuery("SELECT h FROM House h WHERE h.street = :id", House.class);
        query.setParameter("id", entityManager.getReference(Street.class, id));
        return query.getResultList();
    }

}
