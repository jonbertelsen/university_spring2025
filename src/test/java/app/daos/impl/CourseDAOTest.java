package app.daos.impl;

import app.config.HibernateConfig;
import app.entities.Student;
import app.services.Populator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class CourseDAOTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final StudentDAO studentDAO = StudentDAO.getInstance();
    private static Student s1, s2, s3, s4, s5;

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
                em.createQuery("DELETE FROM Student").executeUpdate();
                em.createQuery("DELETE FROM Course").executeUpdate();
                em.createQuery("DELETE FROM Teacher").executeUpdate();
                em.createNativeQuery("ALTER SEQUENCE student_id_seq RESTART WITH 1");
                em.createNativeQuery("ALTER SEQUENCE course_id_seq RESTART WITH 1");
                em.createNativeQuery("ALTER SEQUENCE teacher_id_seq RESTART WITH 1");
            em.getTransaction().commit();
            Student[] students = Populator.populate();
            s1 = students[0];
            s2 = students[1];
            s3 = students[2];
            s4 = students[3];
            s5 = students[4];
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    void getInstance() {
        assertNotEquals(null, emf);
        assertNotEquals(null, studentDAO);
    }

    @Test
    void populate() {
        assertNotEquals(null, s1);
        assertNotEquals(null, s2);
        assertEquals("Anna", s1.getCourse().getTeacher().getName());
        assertEquals("Anna", s2.getCourse().getTeacher().getName());
        assertEquals("Anna", s3.getCourse().getTeacher().getName());
        assertEquals("Benny", s4.getCourse().getTeacher().getName());
        assertEquals("Carl", s5.getCourse().getTeacher().getName());

    }

    @Test
    void create() {


    }

    @Test
    void read() {
    }

    @Test
    void readAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}