package User;

import Course.Course;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Course> courseList = new ArrayList<>();

    public Student() {
        super();
    }

    public Student(Identity identity, String id, String name, String password) {
        this.identity = identity;
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public void addCourse(Course course) {
        courseList.add(course);
    }

    public Course removeCourse(Course course) {
        int courseIndex = courseList.indexOf(course);
        if (courseIndex == -1) {
            return null;
        }
        return courseList.remove(courseIndex);
    }
}