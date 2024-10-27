package System;

import User.*;
import Course.*;

import java.util.*;

public class Database {
    private static Map<String, User> userMap = new HashMap<>();
    private static Map<String, Course> courseMap = new HashMap<>();

    private Database() {
    }

    public static void addUser(User user) {
        userMap.put(user.getId(), user);
    }

    public static User searchUser(String id) {
        return userMap.get(id);
    }

    public static void addCourse(Course course) {
        courseMap.put(course.getIDString(), course);
    }

    public static int getCourseNum() {
        return courseMap.size();
    }

    public static Course searchCourse(String id) {
        return courseMap.get(id);
    }

    public static void removeCourse(String id) {
        courseMap.remove(id);
    }

    public static void printCourseList() {
        List<Course> sortedCourseList = new ArrayList<>(courseMap.values());
        sortedCourseList.sort(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                if (!o1.getTeacher().getName().equals(o2.getTeacher().getName())) {
                    return o1.getTeacher().getName().compareTo(o2.getTeacher().getName());
                }
                return o1.getID() - o2.getID();
            }
        });
        for (Course course : sortedCourseList) {
            System.out.println(course.getTeacher().getName() + " " + course.getIDString() + " " + course.getName()
                    + " " + course.getTime().toString() + " " + String.format("%.1f", course.getCredit()) + " " + course.getPeriod());
        }
    }
}