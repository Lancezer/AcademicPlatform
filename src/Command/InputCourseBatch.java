package Command;

public class InputCourseBatch extends Command {
    public InputCourseBatch(String[] args) {
        this.args = args;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length - 1);
    }

    public void execute() {
    }
}
