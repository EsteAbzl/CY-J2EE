package com.dao;

import com.model.Etudiant;
import com.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TestDAO {

    public void save(Etudiant e) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (e.getId() == null) {
                em.persist(e);
            } else {
                em.merge(e);
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
            Etudiant e = em.find(Etudiant.class, id);
            if (e != null) em.remove(e);
            tx.commit();
        } finally {
            if (tx.isActive()) tx.rollback();
            em.close();
        }
    }

    public Etudiant findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Etudiant.class, id);
        } finally {
            em.close();
        }
    }

    public List<Etudiant> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Etudiant> q = em.createQuery("SELECT e FROM Etudiant e", Etudiant.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}

