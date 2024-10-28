package User;

import Course.Course;

import java.util.ArrayList;

public class Student extends User {
    public enum Type {
        UNDERGRADUATE, PROFESSIONAL, ACADEMIC, DOCTOR
    }

    private ArrayList<Course> courseList = new ArrayList<>();
    private Type type;

    public Student() {
        super();
    }

    public Student(Identity identity, String id, String name, String password) {
        this.identity = identity;
        this.id = id;
        this.name = name;
        this.password = password;
        switch (id.charAt(0)) {
            case 'B':
                this.type = Type.DOCTOR;
                break;
            case 'S':
                this.type = Type.ACADEMIC;
                break;
            case 'Z':
                this.type = Type.PROFESSIONAL;
                break;
            default:
                this.type = Type.UNDERGRADUATE;
        }
    }

    public Type getType() {
        return type;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public int getCourseNum() {
        return courseList.size();
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

    public int compareTo(Student student) {
        if (this.type.compareTo(student.getType()) != 0) {
            return this.type.compareTo(student.getType());
        }
        return this.getID().compareTo(student.getID());
    }

    public void printCourseSchedule() {
        this.courseList.sort(Course::compareTo);
        for (Course course : courseList) {
            System.out.println(course.getTime().toString() + " " + course.getName() + " " + course.getCredit()
                    + " " + course.getPeriod() + " " + course.getTeacher().getName());
        }
    }
}