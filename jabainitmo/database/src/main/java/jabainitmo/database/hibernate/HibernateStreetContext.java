package jabainitmo.database.hibernate;

import java.sql.SQLException;
import java.util.List;

import jabainitmo.database.DbContext;
import jabainitmo.database.Street;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class HibernateStreetContext implements DbContext<Street> {
    @PersistenceContext
    private EntityManager entityManager;

    public HibernateStreetContext() {
        entityManager = Persistence.createEntityManagerFactory("CRM").createEntityManager();
    }

    @Override
    public void save(Street entity) throws SQLException {
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
        entityManager.remove(entityManager.getReference(Street.class, id));
    }

    @Override
    public void deleteAll() throws SQLException {
        entityManager.createQuery("DELETE FROM Street").executeUpdate();
    }

    @Override
    public Street update(Street entity) throws SQLException {
        return entity;
    }

    @Override
    public Street getById(long id) throws SQLException {
        return entityManager.find(Street.class, id);
    }

    @Override
    public List<Street> getAll() throws SQLException {
        return entityManager.createQuery("SELECT s FROM Street s", Street.class).getResultList();
    }

}
