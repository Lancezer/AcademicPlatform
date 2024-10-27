package Command;

import System.State;
import User.*;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class OutputCourseBatch extends Command {
    public OutputCourseBatch(String[] args) {
        this.args = args;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length - 1);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), User.Identity.TEACHER, ERR_MSG[13]);
    }

    public String directoryDeal(String str) {
        if (str.startsWith("./")) {
            return str.substring(2);
        }
        return str;
    }

    public void execute() {
        final String directoryHead = "./data/";
        String directoryOutput = directoryHead + directoryDeal(args[1]);
        try (FileOutputStream file = new FileOutputStream(directoryOutput);
             PrintStream printStream = new java.io.PrintStream(file)) {
            System.setOut(printStream);
            ((Teacher) State.getCurOnlineUser()).printTeacherCourseList(false, false);
        } catch (Exception _) {
        } finally {
            System.setOut(System.out);
            System.out.println(SUCCESS_MEG[14]);
        }
    }
}
