package tn.esprit.ouday_oueslati_4TWIN5.services;

import tn.esprit.ouday_oueslati_4TWIN5.entities.Course;


import java.util.List;

public interface ICourseServices {

    Course addCourse(Course course);
    Course updateCourse(Course course);
    Course retrieveCourse(Long numCourse);
    List<Course> retrieveAll();
    void removeCourse(Long numCourse);

}
