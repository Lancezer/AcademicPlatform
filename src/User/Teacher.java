package User;

import Course.*;

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

    public ArrayList<Course> getCourseList() {
        return new ArrayList<>(courseList.values());
    }

    public void addCourse(Course course) {
        courseList.put(course.getName(), course);
    }

    public Course searchCourse(String name) {
        return courseList.get(name);
    }

    public Course removeCourse(Course course) {
        if (courseList.containsValue(course)) {
            return courseList.remove(course.getName());
        }
        return course;
    }

    public void printTeacherCourseList(boolean isPrintName, boolean isPrintID) {
        List<Course> sortedCourseList = this.getCourseList();
        sortedCourseList.sort(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getID() - o2.getID();
            }
        });
        for (Course course : sortedCourseList) {
            if (isPrintName) {
                System.out.print(this.getName() + " ");
            }
            if (isPrintID) {
                System.out.print(course.getIDString() + " ");
            }
            System.out.println(course.getName() + " " + course.getTime().toString()
                    + " " + String.format("%.1f", course.getCredit()) + " " + course.getPeriod());
        }
    }
}