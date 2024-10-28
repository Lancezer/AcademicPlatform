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
        argsNumCheck(args.length);
        curOnlineUserCheck();
        if (args.length == 1) { // one args
            permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.ADMIN);
            strCheck(args[0], ERR_MSG[3], ARG_FORMAT[0], ARG_FORMAT[1], ARG_FORMAT[2], ARG_FORMAT[3], ARG_FORMAT[4]); // 学工号
            userExistCheck(args[0]); // 用户存在性
        }
    }

    public void execute() {
        User tmp = null;
        if (args.length == 0) { // no args
            tmp = State.getCurOnlineUser();
        } else { // one args
            tmp = Database.searchUser(args[0]);
        }
        System.out.println(SUCCESS_MEG[4] + tmp.getID() + SUCCESS_MEG[5] + tmp.getName() + SUCCESS_MEG[6] + tmp.getIdentityString() + SUCCESS_MEG[7]);
    }
}