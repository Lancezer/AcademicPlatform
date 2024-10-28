package Command;

import System.State;
import User.User;

public class Quit extends Command {
    public Quit() {
    }

    public Quit(String[] str) {
        this.args = str;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len > 0) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void checkCommand() throws IllegalArgumentException {
        argsNumCheck(args.length);
    }

    public void execute() {
        while (!State.isOnlineListEmpty()) {
            User tmp = State.removeOnlineUserByIndex(0);
            System.out.println(tmp.getID() + SUCCESS_MEG[0]);
        }
        System.out.println(SUCCESS_MEG[1]);
    }
}