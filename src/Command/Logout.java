package Command;

import System.*;
import User.User;

public class Logout extends Command {
    public Logout() {
    }

    public Logout(String[] str) {
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
            userOnlineCheck(args[1]); // 用户在线性
        }
    }

    public void execute() {
        if (args.length == 1) { // no args
            State.removeOnlineUserByUserType(State.getCurOnlineUser());
            System.out.println(State.getCurOnlineUser().getId() + SUCCESS_MEG[0]);
            State.setCurOnlineUser(null);
        } else { // one args
            User tmp = Database.searchUser(args[1]);
            State.removeOnlineUserByUserType(tmp);
            if (State.getCurOnlineUser().equals(tmp)) {
                State.setCurOnlineUser(null);
            }
            System.out.println(args[1] + SUCCESS_MEG[0]);
        }
    }
}