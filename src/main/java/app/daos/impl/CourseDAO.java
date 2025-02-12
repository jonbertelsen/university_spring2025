package app.daos.impl;

import app.config.HibernateConfig;
import app.daos.IDAO;
import app.entities.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class CourseDAO implements IDAO<Course, Integer> {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static CourseDAO instance;

    private CourseDAO() {
    }

    public static CourseDAO getInstance() {
        if (instance == null) {
            instance = new CourseDAO();
        }
        return instance;
    }

    @Override
    public Course create(Course course) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        }
        return course;
    }

    @Override
    public Course read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()){
            return em.find(Course.class, integer);
        }
    }

    @Override
    public List<Course> readAll() {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        }
    }

    @Override
    public Course update(Course course) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(course);
            em.getTransaction().commit();
        }
        return course;
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Course course = em.find(Course.class, integer);
            if (course == null) {
                return;
            }
            em.remove(course);
            em.getTransaction().commit();
        }
    }
}
