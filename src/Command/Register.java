package Command;

import System.Database;
import User.*;

public class Register extends Command {
    public Register() {
    }

    public Register(String[] str) {
        this.args = str;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 5) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void userExistCheck(String id) throws IllegalArgumentException {
        if (Database.searchUser(id) != null) {
            throw new IllegalArgumentException(ERR_MSG[4]);
        }
    }

    public void passwordEqualCheck(String password, String password2) throws IllegalArgumentException {
        if (!password.equals(password2)) {
            throw new IllegalArgumentException(ERR_MSG[7]);
        }
    }

    public User.Identity identityMatchByStr(String identity) {
        return switch (identity) {
            case "Student" -> User.Identity.STUDENT;
            case "Teacher" -> User.Identity.TEACHER;
            case "Administrator" -> User.Identity.ADMIN;
            default -> User.Identity.NO_IDENTITY;
        };
    }

    public User.Identity identityMatchById(String id) {
        User.Identity flag = User.Identity.NO_IDENTITY;
        if (id.matches(ARG_FORMAT[0]) || id.matches(ARG_FORMAT[1]) || id.matches(ARG_FORMAT[2])) {
            flag = User.Identity.STUDENT;
        } else if (id.matches(ARG_FORMAT[3])) {
            flag = User.Identity.TEACHER;
        } else if (id.matches(ARG_FORMAT[4])) {
            flag = User.Identity.ADMIN;
        }
        return flag;
    }

    public void identityCheck(String identity, String id) throws IllegalArgumentException {
        User.Identity flag1 = identityMatchByStr(identity);
        if (flag1 == User.Identity.NO_IDENTITY) {
            throw new IllegalArgumentException(ERR_MSG[8]);
        }
    }

    public void checkCommand() throws IllegalArgumentException {
        argsNumCheck(args.length);
        strCheck(args[0], ERR_MSG[3], ARG_FORMAT[0], ARG_FORMAT[1], ARG_FORMAT[2], ARG_FORMAT[3], ARG_FORMAT[4]); // 学工号
        userExistCheck(args[0]); // 用户存在性
        strCheck(args[1], ERR_MSG[5], ARG_FORMAT[5]); // 用户名
        strCheck(args[2], ERR_MSG[6], ARG_FORMAT[6]); // 密码
        passwordEqualCheck(args[2], args[3]); // 密码一致性
        identityCheck(args[4], args[0]); // 身份判断
    }

    public void execute() {
        User user = switch (args[4]) {
            case "Student" -> new Student(User.Identity.STUDENT, args[0], args[1], args[2]);
            case "Teacher" -> new Teacher(User.Identity.TEACHER, args[0], args[1], args[2]);
            case "Administrator" -> new Admin(User.Identity.ADMIN, args[0], args[1], args[2]);
            default -> null;
        };
        Database.addUser(user);
        System.out.println(SUCCESS_MEG[2]);
    }
}