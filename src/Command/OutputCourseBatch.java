package Command;

import System.State;
import User.*;

import java.io.*;

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
        argsNumCheck(args.length);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.TEACHER);
    }

    public void execute() {
        File directory = new File(directoryHead);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String directoryOutput = directoryTransform(args[0]);
        try (
                FileOutputStream file = new FileOutputStream(directoryOutput);
                PrintStream printStream = new java.io.PrintStream(file);
        ) {
            ((Teacher) State.getCurOnlineUser()).printTeacherCourseList(printStream, false, false);
        } catch (Exception _) {
        } finally {
            System.out.println(SUCCESS_MEG[14]);
        }
    }
}
