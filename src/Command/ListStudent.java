package Command;

import Course.Course;
import System.*;
import User.*;

public class ListStudent extends Command {
    public ListStudent(String[] args) {
        this.args = args;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void studentNumCheck() {
        Course course = Database.searchCourse(args[0]);
        if (course.getStudentNum() == 0) {
            throw new IllegalArgumentException(ERR_MSG[29]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), ERR_MSG[11], User.Identity.TEACHER, User.Identity.ADMIN);
        strCheck(args[0], ERR_MSG[24], ARG_FORMAT[9]);
        courseExistCheck(args[0], 0);
        studentNumCheck();
    }

    public void execute() {
        Course course = Database.searchCourse(args[0]);
        course.printStudentList();
        System.out.println(SUCCESS_MEG[16]);
    }
}
