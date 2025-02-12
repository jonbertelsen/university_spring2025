package app.daos.impl;

import app.config.HibernateConfig;
import app.entities.Student;
import app.services.Populator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

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
        Student s6 = Student.builder().email("student6@example.com").name("Kenny").build();
        studentDAO.create(s6);
        assertNotNull(s6.getId());
        assertEquals("Kenny", studentDAO.read(s6.getId()).getName());
        assertEquals(6, studentDAO.readAll().size());
    }

    @Test
    void read() {
        assertEquals("Alice", studentDAO.read(s1.getId()).getName());
        assertEquals("Bob", studentDAO.read(s2.getId()).getName());
        assertEquals("Charlie", studentDAO.read(s3.getId()).getName());
        assertEquals("David", studentDAO.read(s4.getId()).getName());
        assertEquals("Emma", studentDAO.read(s5.getId()).getName());
    }

    @Test
    void readAll() {
        List<Student> students = studentDAO.readAll();
        assertThat(students, hasItem(s1));
        assertThat(students, hasItem(s2));
        assertThat(students, hasItem(s3));
        assertThat(students, hasItem(s4));
        assertThat(students, hasItem(s5));
        assertEquals(5, students.size());
    }

    @Test
    void update() {
        Student updatedStudent = s1.toBuilder().name("Alicia").email("alicia@gmail.com").build();  // Creates an exact copy
        updatedStudent = studentDAO.update(updatedStudent);
        assertEquals("Alicia", studentDAO.read(updatedStudent.getId()).getName());
        assertEquals("alicia@gmail.com", studentDAO.read(updatedStudent.getId()).getEmail());
    }

    @Test
    void delete() {
        studentDAO.delete(s1.getId());
        assertNull(studentDAO.read(s1.getId()));
        assertEquals(4, studentDAO.readAll().size());
    }
}