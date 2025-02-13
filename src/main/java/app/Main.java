package app;

import app.daos.impl.CourseDAO;
import app.entities.Course;
import app.services.Populator;

import java.util.List;

public class Main {

    private static final CourseDAO courseDAO = CourseDAO.getInstance();

    public static void main(String[] args) {

        System.out.println("Hello, World!");

        List<Course> courseList= Populator.populate();
        courseList.forEach(System.out::println);

        Course c1 = courseList.get(0);
        Course c2 = courseList.get(1);
        System.out.println("Underviser på c1: " + c1.getTeacher().getName());


    }
}