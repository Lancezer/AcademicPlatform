import Command.Command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // reopen
//        try {
//            FileInputStream file = new FileInputStream("./input/04.in");
//            System.setIn(file);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        Scanner input = new Scanner(System.in);
        // line index
//        int cnt = 1;
        while (input.hasNextLine()) {
            String str_in = input.nextLine();
            if (str_in.isEmpty()) {
                continue;
            }
            // 按照空格进行分割
            String[] str = str_in.split("\\s+");
            // line index
//            System.out.printf("%3d : ", cnt);
//            cnt += 1;
            // check command
            try {
                Command command = Command.existCheck(str);
                command.checkCommand();
                command.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        input.close();
    }
}