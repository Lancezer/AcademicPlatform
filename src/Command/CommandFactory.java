package Command;

import java.util.Arrays;

public class CommandFactory {
    public static Command createCommand(String[] str) {
        String[] args = Arrays.copyOfRange(str, 1, str.length);
        return switch (str[0]) {
            case "quit" -> new Quit(args);
            case "register" -> new Register(args);
            case "login" -> new Login(args);
            case "logout" -> new Logout(args);
            case "switch" -> new Switch(args);
            case "printInfo" -> new PrintInfo(args);
            case "createCourse" -> new CreateCourse(args);
            case "listCourse" -> new ListCourse(args);
            case "selectCourse" -> new SelectCourse(args);
            case "cancelCourse" -> new CancelCourse(args);
            case "inputCourseBatch" -> new InputCourseBatch(args);
            case "outputCourseBatch" -> new OutputCourseBatch(args);
            case "listStudent" -> new ListStudent(args);
            case "removeStudent" -> new RemoveStudent(args);
            case "listCourseSchedule" -> new ListCourseSchedule(args);
            default -> null;
        };
    }
}