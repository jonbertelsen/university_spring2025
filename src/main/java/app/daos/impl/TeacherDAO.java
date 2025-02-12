package app.daos.impl;

import app.config.HibernateConfig;
import app.daos.IDAO;
import app.entities.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TeacherDAO implements IDAO<Teacher, Integer>{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static TeacherDAO instance;

    private TeacherDAO() {
    }

    public static TeacherDAO getInstance() {
        if (instance == null) {
            instance = new TeacherDAO();
        }
        return instance;
    }


    @Override
    public Teacher create(Teacher teacher) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        }
        return teacher;
    }

    @Override
    public Teacher read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()){
            return em.find(Teacher.class, integer);
        }
    }

    @Override
    public List<Teacher> readAll() {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
        }
    }

    @Override
    public Teacher update(Teacher teacher) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(teacher);
            em.getTransaction().commit();
        }
        return teacher;
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()){
            Teacher teacher = em.find(Teacher.class, integer);
            if (teacher == null) {
                return;
            }
            em.getTransaction().begin();
            em.remove(teacher);
            em.getTransaction().commit();
        }
    }
}
