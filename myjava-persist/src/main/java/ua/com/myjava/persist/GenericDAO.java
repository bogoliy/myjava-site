package ua.com.myjava.persist;

import org.hibernate.criterion.DetachedCriteria;
import org.apache.commons.collections.KeyValue;
import org.hibernate.criterion.Property;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * User: abogoley
 * Date: 10.08.2010
 * Time: 21:26:40
 */
public class GenericDAO<T, ID extends Serializable> extends HibernateDaoSupport {
    private Class<T> persistenceClass;

    public GenericDAO(Class<T> persistenceClass) {
        this.persistenceClass = persistenceClass;
    }

    public T load(ID id) throws DAOException {
        T entity;
        try {
            entity = (T) getHibernateTemplate().load(getPersistenceClass(), id);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot load " + getPersistenceClass().getName() + ", id=" + id, e);
        }
        return entity;
    }

    public T get(ID id) {
        return (T) getHibernateTemplate().get(getPersistenceClass(), id);
    }

    public T merge(T bean) throws DAOException {
        T entity;
        try {
            entity = (T) getHibernateTemplate().merge(bean);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot merge " + bean, e);
        }
        return entity;
    }

    public void saveOrUpdate(T bean) throws DAOException {
        try {
            getHibernateTemplate().saveOrUpdate(bean);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot save or update " + bean, e);
        }
    }

    public void save(T bean) throws DAOException {
        try {
            getHibernateTemplate().save(bean);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot save " + bean, e);
        }
    }

    public void update(T bean) throws DAOException {
        try {
            getHibernateTemplate().update(bean);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot update " + bean, e);
        }
    }

    public void delete(T bean) throws DAOException {
        try {
            getHibernateTemplate().delete(bean);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot delete " + bean, e);
        }
    }

    public void deleteAll(Collection<T> beans) throws DAOException {
        try {
            getHibernateTemplate().deleteAll(beans);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot delete " + getPersistenceClass().getName() + " entities", e);
        }
    }

    public List<T> loadAll() throws DAOException {
        try {
            return getHibernateTemplate().loadAll(getPersistenceClass());
        } catch (DataAccessException e) {
            throw new DAOException("Cannot load " + getPersistenceClass().getName() + " entities", e);
        }
    }

    public void saveOrUpdateAll(Collection<T> beans) throws DAOException {
        try {
            getHibernateTemplate().saveOrUpdateAll(beans);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot save or update " + getPersistenceClass().getName() + " entities", e);
        }
    }

    public List<T> find(KeyValue... pairs) {
        DetachedCriteria criteria = DetachedCriteria.forClass(getPersistenceClass());
        for (KeyValue pair : pairs) {
            criteria.add(Property.forName((String) pair.getKey()).eq(pair.getValue()));
        }
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public Long getMaxId() throws DAOException {
        DetachedCriteria criteria = DetachedCriteria.forClass(getPersistenceClass());
        criteria.setProjection(Property.forName("id").max());
        List data;
        try {
            data = getHibernateTemplate().findByCriteria(criteria);
        } catch (DataAccessException e) {
            throw new DAOException("Cannot define max ID", e);
        }
        if (data.isEmpty()) {
            return 0L;
        }
        return (Long) data.get(0);
    }

    protected Class<T> getPersistenceClass() {
        return persistenceClass;
    }
}
