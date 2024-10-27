package Command;

import Course.*;
import System.*;
import User.*;

import java.util.ArrayList;

public class CreateCourse extends Command {
    private CourseTime courseTime = new CourseTime();
    private double credit;
    private int period;

    public CreateCourse() {
    }

    public CreateCourse(String[] str) {
        this.args = str;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 4) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void courseNumCheck() throws IllegalArgumentException {
        Teacher teacher = (Teacher) State.getCurOnlineUser();
        if (teacher.getCourseNum() >= 10) {
            throw new IllegalArgumentException(ERR_MSG[15]);
        }
    }

    public void courseNameExistCheck() throws IllegalArgumentException {
        Teacher teacher = (Teacher) State.getCurOnlineUser();
        if (teacher.searchCourse(args[1]) != null) {
            throw new IllegalArgumentException(ERR_MSG[17]);
        }
    }

    public void courseConflictCheck() throws IllegalArgumentException {
        Teacher teacher = (Teacher) State.getCurOnlineUser();
        ArrayList<Course> courseList = teacher.getCourseList();
        for (Course course : courseList) {
            if (course.getTime().isConflict(courseTime)) {
                throw new IllegalArgumentException(ERR_MSG[19]);
            }
        }
    }

    public void creditCheck() throws IllegalArgumentException {
        try {
            credit = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERR_MSG[20]);
        }
        if (credit <= 0.0 || credit > 12.0) {
            throw new IllegalArgumentException(ERR_MSG[20]);
        }
    }

    public void periodCheck() throws IllegalArgumentException {
        try {
            period = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERR_MSG[21]);
        }
        if (period <= 0 || period > 1280) {
            throw new IllegalArgumentException(ERR_MSG[21]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length - 1);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), User.Identity.TEACHER, ERR_MSG[13]);
        courseNumCheck();
        strCheck(args[1], ERR_MSG[16], ARG_FORMAT[7]); // 课程名
        courseNameExistCheck();
        courseTime.setCourseTime(args[2]); // 课程时间
        courseConflictCheck(); // 课程时间冲突
        creditCheck(); // 学分
        periodCheck(); // 学时
    }

    public void execute() {
        Teacher teacher = (Teacher) State.getCurOnlineUser();
        Course course = new Course(args[1], this.courseTime, this.credit, this.period, teacher);
        course.setCourseId();
        teacher.addCourse(course);
        Database.addCourse(course);
        System.out.println(SUCCESS_MEG[8] + course.getIDString() + SUCCESS_MEG[11]);
    }
}