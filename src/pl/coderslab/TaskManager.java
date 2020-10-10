package pl.coderslab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
static final String FILE_NAME = "tasks.csv";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String input = scanner.nextLine();
                switch (input){
                    case "exit":
                        break;
                    case "add":
                        break;
                    case "remove":
                        break;
                    case "list":
                        printArray(tasks());
                    default:
                        System.out.println("Please select a correct option");
                }
                options();
            }

        //options();
        System.out.println(Arrays.deepToString(tasks(FILE_NAME)));
    }

    public static void options (){
        System.out.println(ConsoleColors.GREEN_BOLD + "- add");
        System.out.println(ConsoleColors.RED + "- remove");
        System.out.println(ConsoleColors.BLUE + "- list");
        System.out.println(ConsoleColors.RESET + "- exit");
        System.out.println();
        System.out.println("Please select an option");
    }

    public static String [][] tasks (String fileName){

        Path dir = Paths.get(fileName);
        if(!Files.exists(dir)){
            System.out.println("File not exist");
        }

        String [][] array = null;

        try {
            List<String> strings = Files.readAllLines(dir);
            array = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    array[i][j] = split[j];
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }return array;
    }

    public static void printArray (String [][] Array){
        for (int i = 0; i < Array.length; i++){
            System.out.print((i + " : "));
            for (int j = 0; j < Array.length; j++) {
                System.out.print(Array[i][j] + " ");
            }
            System.out.println();
        }
    }

}
