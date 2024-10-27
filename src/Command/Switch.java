package Command;

import System.*;

public class Switch extends Command {
    public Switch(String[] args) {
        this.args = args;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length - 1);
        strCheck(args[1], ERR_MSG[3], ARG_FORMAT[0], ARG_FORMAT[1], ARG_FORMAT[2], ARG_FORMAT[3], ARG_FORMAT[4]); // 学工号
        userExistCheck(args[1]); // 用户存在性
        userOnlineCheck(args[1]); // 用户在线性
    }

    public void execute() {
        State.setCurOnlineUser(Database.searchUser(args[1]));
    }
}
