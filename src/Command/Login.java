package Command;

import System.*;
import User.*;

public class Login extends Command {
    public Login() {
    }

    public Login(String[] str) {
        this.args = str;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 2) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void userOnlineCheck(String id) throws IllegalArgumentException {
        if (State.isOnline(id)) {
            throw new IllegalArgumentException(id + ERR_MSG[10]);
        }
    }

    public void passwordCheck(String id, String password) throws IllegalArgumentException {
        if (!Database.searchUser(id).checkPassword(password)) {
            throw new IllegalArgumentException(ERR_MSG[11]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length - 1);
        strCheck(args[1], ERR_MSG[3], ARG_FORMAT[0], ARG_FORMAT[1], ARG_FORMAT[2], ARG_FORMAT[3], ARG_FORMAT[4]); // 学工号
        userExistCheck(args[1]); // 用户存在性
        userOnlineCheck(args[1]); // 用户在线性
        passwordCheck(args[1], args[2]); // 密码正确性
    }

    public void execute() {
        State.addOnlineUser(Database.searchUser(args[1]));
        State.setCurOnlineUser(Database.searchUser(args[1]));
        System.out.println(SUCCESS_MEG[3] + args[1]);
    }
}