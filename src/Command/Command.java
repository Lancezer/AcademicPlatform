package Command;

import System.*;
import User.*;

import java.io.IOException;

public abstract class Command {
    protected String[] args;

    public static Command existCheck(String[] str) throws IOException {
        for (String systemCommand : SYSTEM_COMMANDS) {
            if (systemCommand.equals(str[0])) {
                return CommandFactory.createCommand(str);
            }
        }
        throw new IOException(ERR_MSG[0] + str[0] + ERR_MSG[1]);
    }

    public static void strCheck(String str, String errMessage, String... format) throws IllegalArgumentException {
        for (String f : format) {
            if (str.matches(f)) {
                return;
            }
        }
        throw new IllegalArgumentException(errMessage);
    }

    public abstract void argsNumCheck(int len) throws IllegalArgumentException;

    public abstract void checkCommand();

    public abstract void execute();

    public void curOnlineUserCheck() {
        if (State.getCurOnlineUser() == null) {
            throw new IllegalArgumentException(ERR_MSG[12]);
        }
    }

    public void permissionCheck(User user, User.Identity identity, String errMessage) {
        if (user.getIdentity() != identity) {
            throw new IllegalArgumentException(errMessage);
        }
    }

    public void userExistCheck(String id) throws IllegalArgumentException {
        if (Database.searchUser(id) == null) {
            throw new IllegalArgumentException(ERR_MSG[9]);
        }
    }

    public void userOnlineCheck(String id) throws IllegalArgumentException {
        if (!State.isOnline(id)) {
            throw new IllegalArgumentException(id + ERR_MSG[14]);
        }
    }

    public static final String[] SYSTEM_COMMANDS = {
            "quit",
            "register", "login", "logout", "switch",
            "printInfo",
            "createCourse", "listCourse", "selectCourse", "cancelCourse",
            "outputCourseBatch", "inputCourseBatch",
    };

    public static final String[] ARG_FORMAT = {
            "^(19|2[0-4])(0[1-9]|[1-3][0-9]|4[0-3])([1-6])(00[1-9]|0[1-9][0-9]|[1-9][0-9]{2})$", // 本科生学号 // 0
            "^(S|Z)Y2[1-4](0[1-9]|[1-3][0-9]|4[0-3])([1-6])(0[1-9]|[1-9][0-9])$", // 研究生学号 // 1
            "^BY(19|2[0-4])(0[1-9]|[1-3][0-9]|4[0-3])([1-6])(0[1-9]|[1-9][0-9])$", // 博士生学号 // 2
            "^(0000[1-9]|000[1-9][0-9]|00[1-9][0-9]{2}|0[1-9][0-9]{3}|[1-9][0-9]{4})$", // 教师工号 // 3
            "^AD(00[1-9]|0[1-9][0-9]|[1-9][0-9]{2})$", // 管理员工号 // 4
            "^[a-zA-Z][a-zA-Z_]{3,15}$", // 用户名 // 5
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@_%$])[a-zA-Z0-9@_%$]{6,16}$", // 密码 // 6
            "^[a-zA-Z](?=.*[a-zA-Z])[a-zA-Z0-9_-]{0,19}$", // 课程名 // 7
            "^[1-7]_([1-9]|1[0-4])-([1-9]|1[0-4])$", // 课程时间 // 8
            "^C-([1-9]\\d*)$", // 课程编号 // 9
    };

    public static final String[] ERR_MSG = {
            "Command '", // Command '命令' not found // 0
            "' not found", // Command '命令' not found // 1
            "Illegal argument count", // 2
            "Illegal user id", // 3
            "User id exists", // 4
            "Illegal user name", // 5
            "Illegal password", // 6
            "Passwords do not match", // 7
            "Illegal identity", // 8
            "User does not exist", // 9
            " is online", // 学工号 is online // 10
            "Wrong password", // 11
            "No one is online", // 12
            "Permission denied", // 13
            " is not online", // 学工号 is not online // 14
            "Course count reaches limit", // 15
            "Illegal course name", // 16
            "Course name exists", // 17
            "Illegal course time", // 18
            "Course time conflicts", // 19
            "Illegal course credit", // 20
            "Illegal course period", // 21
            "Course does not exist", // 22
            "User id does not belong to a Teacher", // 23
            "Illegal course id", // 24
            "Course capacity is full", // 25
    };

    public static final String[] SUCCESS_MEG = {
            " Bye~", // 学工号 Bye~ // 0
            "----- Good Bye! -----", // 1
            "Register success", // 2
            "Welcome to ACP, ", // Welcome to ACP, 学工号 // 3
            /*
            User id: id
            Name: username
            Type: Administrator/Teacher/Student
            Print information success
            */
            "User id: ", // 4
            "\nName: ", // 5
            "\nType: ", // 6
            "\nPrint information success", // 7
            "Create course success (courseId: ", // Create course success (courseId: C-X) // 8
            "Select course success (courseId: ", // Select course success (courseId: C-X) // 9
            "Cancel course success (courseId: ", // Cancel course success (courseId: C-X) // 10
            ")", // 11 // Create course success (courseId: C-X), Select course success (courseId: C-X), Cancel course success (courseId: C-X)
            "List course success", // 12
            "switch to ", // 13 // switch to 学工号
            "Output course batch success", // 14
    };
}