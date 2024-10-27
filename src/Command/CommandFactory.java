package Command;

public class CommandFactory {
    public static Command createCommand(String[] str) {
        return switch (str[0]) {
            case "quit" -> new Quit(str);
            case "register" -> new Register(str);
            case "login" -> new Login(str);
            case "logout" -> new Logout(str);
            case "switch" -> new Switch(str);
            case "printInfo" -> new PrintInfo(str);
            case "createCourse" -> new CreateCourse(str);
            case "listCourse" -> new ListCourse(str);
            case "selectCourse" -> new SelectCourse(str);
            case "cancelCourse" -> new CancelCourse(str);
            case "inputCourseBatch" -> new InputCourseBatch(str);
            case "outputCourseBatch" -> new OutputCourseBatch(str);
            default -> null;
        };
    }
}