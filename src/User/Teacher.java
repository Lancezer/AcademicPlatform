package User;

import Course.*;

import java.io.PrintStream;
import java.util.*;

public class Teacher extends User {
    private Map<String, Course> courseList = new HashMap<>();

    public Teacher() {
        super();
    }

    public Teacher(Identity identity, String id, String name, String password) {
        this.identity = identity;
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getCourseNum() {
        return courseList.size();
    }

    public int getCourseLimit() {
        return 10;
    }

    public void addCourse(Course course) {
        courseList.put(course.getName(), course);
    }

    public Course searchCourse(String name) {
        return courseList.get(name);
    }

    public void removeCourse(Course course) {
        if (courseList.containsValue(course)) {
            courseList.remove(course.getName());
        }
    }

    public boolean isCourseConflict(CourseTime courseTime) {
        for (Course course : courseList.values()) {
            if (course.getTime().isConflict(courseTime)) {
                return true;
            }
        }
        return false;
    }

    public void printTeacherCourseList(PrintStream printStream, boolean isPrintName, boolean isPrintID) {
        List<Course> sortedCourseList = new ArrayList<>(courseList.values());
        sortedCourseList.sort(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getID() - o2.getID();
            }
        });
        for (Course course : sortedCourseList) {
            if (isPrintName) {
                printStream.print(this.getName() + " ");
            }
            if (isPrintID) {
                printStream.print(course.getIDString() + " ");
            }
            printStream.println(course.getName() + " " + course.getTime().toString()
                    + " " + String.format("%.1f", course.getCredit()) + " " + course.getPeriod());
        }
    }

    public boolean searchStudent(String studentID) {
        for (Course course : courseList.values()) {
            if (course.searchStudent(studentID) != null) {
                return true;
            }
        }
        return false;
    }

    public void removeStudent(String studentID) {
        for (Course course : courseList.values()) {
            Student student = course.searchStudent(studentID);
            if (student != null) {
                course.removeStudent(student);
                student.removeCourse(course);
            }
        }
    }
}