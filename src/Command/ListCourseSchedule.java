package Command;

import System.*;
import User.*;

public class ListCourseSchedule extends Command {
    public ListCourseSchedule(String[] args) {
        this.args = args;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len > 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length);
        curOnlineUserCheck();
        if (args.length == 0) {
            permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.STUDENT);
        } else {
            permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.ADMIN);
            strCheck(args[0], ERR_MSG[3], ARG_FORMAT[0], ARG_FORMAT[1], ARG_FORMAT[2], ARG_FORMAT[3], ARG_FORMAT[4], ARG_FORMAT[5]);
            userExistCheck(args[0]);
            studentIdentityCheck(args[0]);
        }
        studentCourseNumCheck(args.length == 0 ? State.getCurOnlineUser().getID() : args[0]);
    }

    public void execute() {
        Student student = null;
        if (args.length == 0) {
            student = (Student) State.getCurOnlineUser();
        } else {
            student = (Student) Database.searchUser(args[0]);
        }
        student.printCourseSchedule();
        System.out.println(SUCCESS_MEG[18]);
    }
}
