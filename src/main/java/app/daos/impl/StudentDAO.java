package app.daos.impl;

import app.config.HibernateConfig;
import app.daos.IDAO;
import app.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class StudentDAO implements IDAO<Student, Integer> {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static StudentDAO instance;

    private StudentDAO() {
    }

    public static StudentDAO getInstance() {
        if (instance == null) {
            instance = new StudentDAO();
        }
        return instance;
    }

    @Override
    public Student create(Student student) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        }
        return student;
    }

    @Override
    public Student read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()){
            return em.find(Student.class, integer);
        }
    }

    @Override
    public List<Student> readAll() {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        }
    }

    @Override
    public Student update(Student student) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(student);
            em.getTransaction().commit();
        }
        return student;
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()){
            Student student = em.find(Student.class, integer);
            if (student != null) {
                em.getTransaction().begin();
                em.remove(student);
                em.getTransaction().commit();
            }
        }
    }
}
