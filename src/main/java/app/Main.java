package app;

import app.daos.impl.CourseDAO;
import app.services.Populator;

public class Main {

    private static final CourseDAO courseDAO = CourseDAO.getInstance();

    public static void main(String[] args) {

        System.out.println("Hello, World!");

        Populator.populate();

    }
}