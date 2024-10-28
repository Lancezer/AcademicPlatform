package Command;

import User.*;
import System.*;
import Course.*;

import java.util.ArrayList;

public class SelectCourse extends Command {
    public SelectCourse() {
    }

    public SelectCourse(String[] str) {
        this.args = str;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void courseTimeConflictCheck(String id) throws IllegalArgumentException {
        Student student = (Student) State.getCurOnlineUser();
        Course chosenCourse = Database.searchCourse(id);
        if (student.isCourseConflict(chosenCourse.getTime())) {
            throw new IllegalArgumentException(ERR_MSG[19]);

        }
    }

    public void courseStudentLimitCheck(String id) throws IllegalArgumentException {
        Course course = Database.searchCourse(id);
        if (course.getStudentNum() >= course.getStudentNumLimit()) {
            throw new IllegalArgumentException(ERR_MSG[25]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.STUDENT);
        strCheck(args[0], ERR_MSG[24], ARG_FORMAT[9]); // 课程编号
        courseExistCheck(args[0], 0); // 课程存在性
        courseTimeConflictCheck(args[0]); // 课程时间冲突性
        courseStudentLimitCheck(args[0]); // 课程学生数限制性
    }

    public void execute() {
        Student student = (Student) State.getCurOnlineUser();
        Course course = Database.searchCourse(args[0]);
        student.addCourse(course);
        course.addStudent(student);
        System.out.println(SUCCESS_MEG[9] + course.getIDString() + SUCCESS_MEG[11]);
    }
}