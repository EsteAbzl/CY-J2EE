package com.dao;

import com.model.Test;
import com.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TestDAO {

    public void save(Test t) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (t.getId() == null) {
                em.persist(t);
            } else {
                em.merge(t);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void delete(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Test t = em.find(Test.class, id);
            if (t != null) em.remove(t);
            tx.commit();
        } finally {
            if (tx.isActive()) tx.rollback();
            em.close();
        }
    }

    public Test findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Test.class, id);
        } finally {
            em.close();
        }
    }

    public List<Test> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Test> q = em.createQuery("SELECT Test FROM Test", Test.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}

