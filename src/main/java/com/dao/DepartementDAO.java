package com.dao;

import com.model.Departement;
import com.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class DepartementDAO {

    public Departement findByName(String nom) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Departement> q = em.createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class);
            q.setParameter("nom", nom);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }
}
