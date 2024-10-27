package Command;

import System.*;
import User.*;
import Course.*;

import java.util.ArrayList;

public class CancelCourse extends Command {
    public CancelCourse() {
    }

    public CancelCourse(String[] str) {
        this.args = str;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void courseExistCheck(String id) throws IllegalArgumentException {
        if (Database.searchCourse(id) == null) {
            throw new IllegalArgumentException(ERR_MSG[22]);
        }
        User user = State.getCurOnlineUser();
        if (user.getIdentity() == User.Identity.STUDENT) {
            Student student = (Student) user;
            Course course = Database.searchCourse(id);
            if (!student.getCourseList().contains(course)) {
                throw new IllegalArgumentException(ERR_MSG[22]);
            }
        } else if (user.getIdentity() == User.Identity.TEACHER) {
            Teacher teacher = (Teacher) user;
            Course course = Database.searchCourse(id);
            if (teacher.searchCourse(course.getName()) == null) {
                throw new IllegalArgumentException(ERR_MSG[22]);
            }
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length - 1);
        curOnlineUserCheck();
        strCheck(args[1], ERR_MSG[24], ARG_FORMAT[9]); // 课程编号
        courseExistCheck(args[1]); // 课程存在
    }

    public void execute() {
        User user = State.getCurOnlineUser();
        Course course = Database.searchCourse(args[1]);
        if (user.getIdentity() == User.Identity.STUDENT) {
            Student student = (Student) user;
            student.removeCourse(course);
            course.removeStudent(student);
        } else if (user.getIdentity() == User.Identity.TEACHER) {
            Teacher teacher = (Teacher) user;
            teacher.removeCourse(course);
            ArrayList<Student> studentList = course.getStudentList();
            for (Student student : studentList) {
                student.removeCourse(course);
            }
            Database.removeCourse(args[1]);
        } else if (user.getIdentity() == User.Identity.ADMIN) {
            ArrayList<Student> studentList = course.getStudentList();
            for (Student student : studentList) {
                student.removeCourse(course);
            }
            course.getTeacher().removeCourse(course);
            Database.removeCourse(args[1]);
        }
        System.out.println(SUCCESS_MEG[10] + course.getIDString() + SUCCESS_MEG[11]);
    }
}