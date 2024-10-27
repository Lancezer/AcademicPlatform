package Command;

import User.*;
import System.*;

public class ListCourse extends Command {
    public ListCourse() {
    }

    public ListCourse(String[] str) {
        this.args = str;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len > 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void courseNumCheck() {
        User user = State.getCurOnlineUser();
        if (user.getIdentity() == User.Identity.STUDENT || user.getIdentity() == User.Identity.ADMIN) {
            if (Database.getCourseNum() == 0) {
                throw new IllegalArgumentException(ERR_MSG[22]);
            }
        } else if (user.getIdentity() == User.Identity.TEACHER) {
            Teacher teacher = (Teacher) user;
            if (teacher.getCourseNum() == 0) {
                throw new IllegalArgumentException(ERR_MSG[22]);
            }
        }
    }

    public void courseNumCheck(String id) {
        Teacher teacher = (Teacher) Database.searchUser(id);
        if (teacher.getCourseNum() == 0) {
            throw new IllegalArgumentException(ERR_MSG[22]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length - 1); // 参数数量检查
        curOnlineUserCheck(); // 在线检查
        if (args.length == 1) {
            courseNumCheck(); // 课程数量
        } else if (args.length > 1) {
            permissionCheck(State.getCurOnlineUser(), User.Identity.ADMIN, ERR_MSG[13]); // 权限检查
            strCheck(args[1], ERR_MSG[3], ARG_FORMAT[0], ARG_FORMAT[1], ARG_FORMAT[2], ARG_FORMAT[3]); // 用户ID合法性
            userExistCheck(args[1]); // 用户存在性
            permissionCheck(Database.searchUser(args[1]), User.Identity.TEACHER, ERR_MSG[23]); // 查询身份
            courseNumCheck(args[1]); // 课程数量
        }
    }

    public void execute() {
        if (args.length == 1) {
            User user = State.getCurOnlineUser();
            if (user.getIdentity() == User.Identity.TEACHER) {
                ((Teacher) user).printTeacherCourseList(false, true);
            } else {
                Database.printCourseList();
            }
        } else if (args.length > 1) {
            User user = Database.searchUser(args[1]);
            ((Teacher) user).printTeacherCourseList(true, true);
        }
        System.out.println(SUCCESS_MEG[12]);
    }
}