package Command;

import System.*;
import User.User;

public class PrintInfo extends Command {
    public PrintInfo() {
    }

    public PrintInfo(String[] str) {
        this.args = str;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len > 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length - 1);
        curOnlineUserCheck();
        if (args.length == 2) { // one args
            permissionCheck(State.getCurOnlineUser(), User.Identity.ADMIN, ERR_MSG[13]);
            strCheck(args[1], ERR_MSG[3], ARG_FORMAT[0], ARG_FORMAT[1], ARG_FORMAT[2], ARG_FORMAT[3], ARG_FORMAT[4]); // 学工号
            userExistCheck(args[1]); // 用户存在性
        }
    }

    public void execute() {
        User tmp = null;
        if (args.length == 1) { // no args
            tmp = State.getCurOnlineUser();
        } else { // one args
            tmp = Database.searchUser(args[1]);
        }
        System.out.println(SUCCESS_MEG[4] + tmp.getId() + SUCCESS_MEG[5] + tmp.getName() + SUCCESS_MEG[6] + tmp.getIdentityString() + SUCCESS_MEG[7]);
    }
}