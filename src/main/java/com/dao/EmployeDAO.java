package com.dao;

import com.model.Employe;
import com.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class EmployeDAO {

    public Employe findByEmail(String email) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Employe> q = em.createQuery("SELECT e FROM Employe e WHERE e.email = :email", Employe.class);
            q.setParameter("email", email);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public Employe save(Employe employe) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (employe.getId() == null) {
                em.persist(employe);
            } else {
                employe = em.merge(employe);
            }
            em.getTransaction().commit();
            return employe;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
