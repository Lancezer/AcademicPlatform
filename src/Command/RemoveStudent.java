package Command;

import System.*;
import User.*;
import Course.*;

import java.util.ArrayList;

public class RemoveStudent extends Command {
    public RemoveStudent(String[] args) {
        this.args = args;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len < 1 || len > 2) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void studentCourseSelectCheck(String studentID, String... courseID) {
        studentCourseNumCheck(studentID);
        User user = State.getCurOnlineUser();
        if (courseID.length == 0 && user.getIdentity() == User.Identity.TEACHER) {
            Teacher teacher = (Teacher) user;
            ArrayList<Course> courseList = teacher.getCourseList();
            for (Course course : courseList) {
                if (course.searchStudent(studentID) != null) {
                    return;
                }
            }
            throw new IllegalArgumentException(ERR_MSG[31]);
        } else if (courseID.length == 1) {
            Course course = Database.searchCourse(courseID[0]);
            if (course.searchStudent(studentID) == null) {
                throw new IllegalArgumentException(ERR_MSG[31]);
            }
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.TEACHER, User.Identity.ADMIN);
        strCheck(args[0], ERR_MSG[3], ARG_FORMAT[0], ARG_FORMAT[1], ARG_FORMAT[2]);
        userExistCheck(args[0]);
        studentIdentityCheck(args[0]);
        if (args.length == 2) {
            strCheck(args[1], ERR_MSG[24], ARG_FORMAT[9]);
            courseExistCheck(args[1]);
        }
        if (args.length == 2) {
            studentCourseSelectCheck(args[0], args[1]);
        } else {
            studentCourseSelectCheck(args[0]);
        }
    }

    public void execute() {
        Student student = (Student) Database.searchUser(args[0]);
        if (args.length == 2) {
            Course course = Database.searchCourse(args[1]);
            student.removeCourse(course);
            course.removeStudent(student);
        } else {
            User user = State.getCurOnlineUser();
            if (user.getIdentity() == User.Identity.TEACHER) {
                Teacher teacher = (Teacher) user;
                ArrayList<Course> courseList = teacher.getCourseList();
                for (Course course : courseList) {
                    if (course.searchStudent(args[0]) != null) {
                        student.removeCourse(course);
                        course.removeStudent(student);
                    }
                }
            } else {
                ArrayList<Course> courseList = student.getCourseList();
                for (Course course : courseList) {
                    course.removeStudent(student);
                }
                student.getCourseList().clear();
            }
        }
        System.out.println(SUCCESS_MEG[17]);
    }
}