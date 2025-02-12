package app.services;

import app.config.HibernateConfig;
import app.entities.Course;
import app.entities.Student;
import app.entities.Teacher;
import app.enums.CourseName;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class Populator {
    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    public static Student[] populate() {
        populateTeachers();
        populateStudents();
        populateCourses();
        setupCourseTeachers();
        return setupCourseStudents();
    }

    private static void populateTeachers() {
        try (EntityManager em = emf.createEntityManager()){
            List<Teacher> teachers = List.of(
                    Teacher.builder().email("teacher1@example.com").name("Anna").zoom("zoom1").build(),
                    Teacher.builder().email("teacher2@example.com").name("Benny").zoom("zoom2").build(),
                    Teacher.builder().email("teacher3@example.com").name("Carl").zoom("zoom3").build(),
                    Teacher.builder().email("teacher4@example.com").name("Dennis").zoom("zoom4").build(),
                    Teacher.builder().email("teacher5@example.com").name("Erna").zoom("zoom5").build()
            );
            em.getTransaction().begin();
            teachers.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    private static void populateStudents() {
        try (EntityManager em = emf.createEntityManager()){
            List<Student> students = List.of(
                    Student.builder().email("student1@example.com").name("Alice").build(),
                    Student.builder().email("student2@example.com").name("Bob").build(),
                    Student.builder().email("student3@example.com").name("Charlie").build(),
                    Student.builder().email("student4@example.com").name("David").build(),
                    Student.builder().email("student5@example.com").name("Emma").build()
            );

            em.getTransaction().begin();
            students.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    private static void populateCourses() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Course> courses = List.of(
                    Course.builder().courseName(CourseName.PROGRAMMING).description("Java Programming Course")
                          .startDate(LocalDate.of(2025, 3, 1)).endDate(LocalDate.of(2025, 6, 1)).build(),
                    Course.builder().courseName(CourseName.PROGRAMMING).description("Python Programming Course")
                          .startDate(LocalDate.of(2025, 4, 1)).endDate(LocalDate.of(2025, 7, 1)).build(),
                    Course.builder().courseName(CourseName.WEBDEVELOPMENT).description("Full Stack Web Development")
                          .startDate(LocalDate.of(2025, 5, 1)).endDate(LocalDate.of(2025, 8, 1)).build(),
                    Course.builder().courseName(CourseName.DATASCIENCE).description("Data Science with Machine Learning")
                          .startDate(LocalDate.of(2025, 6, 1)).endDate(LocalDate.of(2025, 9, 1)).build(),
                    Course.builder().courseName(CourseName.SECURITY).description("Cybersecurity Fundamentals")
                          .startDate(LocalDate.of(2025, 7, 1)).endDate(LocalDate.of(2025, 10, 1)).build()
            );
            em.getTransaction().begin();
            courses.forEach(em::persist);
            em.getTransaction().commit();

        }
    }

    private static void setupCourseTeachers() {
        try (EntityManager em = emf.createEntityManager()) {
            Course course1 = em.find(Course.class, 1);
            Course course2 = em.find(Course.class, 2);
            Course course3 = em.find(Course.class, 3);
            Course course4 = em.find(Course.class, 4);
            Course course5 = em.find(Course.class, 5);
            Teacher teacher1 = em.find(Teacher.class, 1);
            Teacher teacher2 = em.find(Teacher.class, 2);
            Teacher teacher3 = em.find(Teacher.class, 3);
            Teacher teacher4 = em.find(Teacher.class, 4);
            Teacher teacher5 = em.find(Teacher.class, 5);

            teacher1.addCourse(course1);
            teacher1.addCourse(course2);
            teacher2.addCourse(course2);
            teacher3.addCourse(course3);
            teacher4.addCourse(course4);
            teacher5.addCourse(course5);

            em.getTransaction().begin();
            em.merge(course1);
            em.merge(course2);
            em.merge(course3);
            em.merge(course4);
            em.merge(course5);
            em.getTransaction().commit();
        }
    }

    private static Student[] setupCourseStudents(){
        try (EntityManager em = emf.createEntityManager()) {
            Course course1 = em.find(Course.class, 1);
            Course course2 = em.find(Course.class, 2);
            Course course3 = em.find(Course.class, 3);
            Course course4 = em.find(Course.class, 4);
            Course course5 = em.find(Course.class, 5);
            Student student1 = em.find(Student.class, 1);
            Student student2 = em.find(Student.class, 2);
            Student student3 = em.find(Student.class, 3);
            Student student4 = em.find(Student.class, 4);
            Student student5 = em.find(Student.class, 5);

            course1.addStudent(student1);
            course1.addStudent(student2);
            course1.addStudent(student3);
            course2.addStudent(student4);
            course3.addStudent(student5);

            em.getTransaction().begin();
            em.merge(course1);
            em.merge(course2);
            em.getTransaction().commit();
            return new Student[]{student1, student2, student3, student4, student5};
        }
    }

}