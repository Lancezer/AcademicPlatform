package Command;

import System.*;
import User.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class InputCourseBatch extends Command {
    String fileDirectory;

    public InputCourseBatch(String[] args) {
        this.args = args;
    }

    public void argsNumCheck(int len) throws IllegalArgumentException {
        if (len != 1) {
            throw new IllegalArgumentException(ERR_MSG[2]);
        }
    }

    public void fileDirectoryCheck(String filename) {
        this.fileDirectory = directoryTransform(filename);
        File file = new File(this.fileDirectory);
        if (!file.exists()) {
            throw new IllegalArgumentException(ERR_MSG[26]);
        }
        if (file.isDirectory()) {
            throw new IllegalArgumentException(ERR_MSG[27]);
        }
    }

    public void checkCommand() {
        argsNumCheck(args.length);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.TEACHER);
        fileDirectoryCheck(args[0]);
    }

    public void execute() {
        try (FileInputStream fileInputStream = new FileInputStream(this.fileDirectory)) {
            Scanner input02 = new Scanner(fileInputStream);
            while (input02.hasNextLine()) {
                String str_in02 = input02.nextLine();
                if (str_in02.isEmpty()) {
                    continue;
                }
                // 按照空格进行分割
                String[] str02 = str_in02.split("\\s+");
                try {
                    CreateCourse command = new CreateCourse(str02);
                    command.courseNumCheck();
                    command.courseNameExistCheck(1);
                    command.setCourseTime(str02[1]); // 课程时间
                    command.courseConflictCheck(command.getCourseTime()); // 课程时间冲突
                    command.creditCheck(); // 学分
                    command.periodCheck(); // 学时
                    command.execute();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            input02.close();
        } catch (Exception e) {
        } finally {
            System.out.println(SUCCESS_MEG[15]);
        }
    }
}
