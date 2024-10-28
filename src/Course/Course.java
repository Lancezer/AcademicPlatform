package Course;

import User.*;
import System.*;

import java.util.ArrayList;

public class Course {
    private String name;
    private int id;
    private CourseTime time;
    private double credit;
    private int period;
    private Teacher teacher;
    private ArrayList<Student> studentList = new ArrayList<>();

    private static int cnt = 0;

    public Course() {
        this.name = null;
        this.time = null;
        this.credit = 0.0;
        this.period = 0;
        this.teacher = null;
    }

    public Course(String name, CourseTime time, double credit, int period, Teacher teacher) {
        this.name = name;
        this.time = time;
        this.credit = credit;
        this.period = period;
        this.teacher = teacher;
    }

    public void setCourseId() {
        this.id = ++cnt;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public String getIDString() {
        return String.format("C-" + this.id);
    }

    public CourseTime getTime() {
        return this.time;
    }

    public double getCredit() {
        return this.credit;
    }

    public int getPeriod() {
        return this.period;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public int compareTo(Course course) {
        if (this.time.getDay() != course.getTime().getDay()) {
            return this.time.getDay() - course.getTime().getDay();
        }
        return this.time.getStart() - course.getTime().getStart();
    }

    public int getStudentNumLimit() {
        return 30;
    }

    public int getStudentNum() {
        return studentList.size();
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public Student searchStudent(String id) {
        Student student = (Student) Database.searchUser(id);
        return studentList.contains(student) ? student : null;
    }

    public void removeStudent(Student student) {
        studentList.remove(student);
    }

    public void removeCourse() {
        for (Student student : studentList) {
            student.removeCourse(this);
        }
        studentList.clear();
        this.teacher.removeCourse(this);
        Database.removeCourse(this.getIDString());
    }

    public void printStudentList() {
        ArrayList<Student> sortedStudentList = new ArrayList<>(studentList);
        sortedStudentList.sort((o1, o2) -> o1.compareTo(o2));
        for (Student student : sortedStudentList) {
            System.out.println(student.getID() + ": " + student.getName());
        }
    }
}