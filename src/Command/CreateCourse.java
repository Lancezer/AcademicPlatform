package Command;

import Course.*;
import System.*;
import User.*;

public class CreateCourse extends Command {
    private CourseTime courseTime = new CourseTime();
    private double credit;
    private int period;

    public CreateCourse() {
    }

    public CreateCourse(String[] str) {
        this.args = str;
    }

    public CourseTime getCourseTime() {
        return courseTime;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 4) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void setCourseTime(String time) {
        this.courseTime.setCourseTime(time);
    }

    public void creditCheck() throws IllegalArgumentException {
        try {
            credit = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERR_MSG[20]);
        }
        if (credit <= 0.0 || credit > 12.0) {
            throw new IllegalArgumentException(ERR_MSG[20]);
        }
    }

    public void periodCheck() throws IllegalArgumentException {
        try {
            period = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERR_MSG[21]);
        }
        if (period <= 0 || period > 1280) {
            throw new IllegalArgumentException(ERR_MSG[21]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.TEACHER);
        courseNumCheck();
        strCheck(args[0], ERR_MSG[16], ARG_FORMAT[7]); // 课程名
        courseNameExistCheck(0);
        setCourseTime(args[1]);
        courseConflictCheck(courseTime); // 课程时间冲突
        creditCheck(); // 学分
        periodCheck(); // 学时
    }

    public void execute() {
        Teacher teacher = (Teacher) State.getCurOnlineUser();
        Course course = new Course(args[0], this.courseTime, this.credit, this.period, teacher);
        course.setCourseId();
        teacher.addCourse(course);
        Database.addCourse(course);
        System.out.println(SUCCESS_MEG[8] + course.getIDString() + SUCCESS_MEG[11]);
    }
}