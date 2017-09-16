package com.common.persistence;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Kirill Stoianov on 15/09/17.
 */
@Transactional
public abstract class GenericDaoImpl<E, PK extends Serializable> implements GenericDao<E, PK> {

    public static final String ID_COLUMN_NAME = "id";

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    @Transactional
    public void persist(E persistentEntity) {
        this.hibernateTemplate.persist(persistentEntity);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public PK save(E newInstance) {
        return (PK) this.hibernateTemplate.save(newInstance);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findById(PK id) {
        return (E) this.hibernateTemplate.get(getEntityClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        return (List<E>) this.hibernateTemplate.findByCriteria(createDetachedCriteria());
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAllByProperty(String propertyName, Object value) {
        DetachedCriteria criteria = createDetachedCriteria();
        criteria.add(Restrictions.eq(propertyName, value));
        return (List<E>) this.hibernateTemplate.findByCriteria(criteria);
    }

    public List<E> findByExample(E object) {
        return this.hibernateTemplate.findByExample(object, 0, 1);
    }

    public List<E> findByExample(E object, int firstResult, int maxResults) {
        return this.hibernateTemplate.findByExample(object,
                firstResult, maxResults);
    }

    @Override
    public void update(E transientObject) {
        this.hibernateTemplate.update(transientObject);
    }

    @Override
    public void saveOrUpdate(E transientObject) {
        this.hibernateTemplate.saveOrUpdate(transientObject);
    }

    @Override
    public void delete(E persistentObject) {
        this.hibernateTemplate.delete(persistentObject);
    }

    @Override
    public void delete(PK id) {
        final E entity = this.findById(id);
        if (entity != null) this.delete(entity);
    }

    @Override
    public boolean isExists(PK id) {
        return this.findById(id) != null;
    }


    protected abstract Class<E> getEntityClass();

    private DetachedCriteria createDetachedCriteria() {
        return DetachedCriteria.forClass(getEntityClass());
    }
}
