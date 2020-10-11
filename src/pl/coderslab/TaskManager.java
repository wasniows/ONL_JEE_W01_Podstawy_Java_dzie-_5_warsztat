package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static final String FILE_NAME = "tasks.csv";
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = loadFromFile(FILE_NAME);
        options();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "exit" -> {
                    saveTabToFile(FILE_NAME, tasks);
                    System.exit(0);
                }
                case "add" -> {
                    addTask();
                    System.out.println("New task was added");
                    System.out.println();
                }
                case "remove" -> {
                    removeTask(tasks, getNubmer());
                    System.out.println("Task was deleted");
                    System.out.println();
                }
                case "list" -> {
                    System.out.println();
                    printArray(tasks);
                    System.out.println();
                }
                default -> {
                    System.out.println("Please select correct option");
                    System.out.println();
                }
            }
            options();
        }

    }

    //metoda do wyświetlania listy dostpęnych opcji
    public static void options() {
        System.out.println(ConsoleColors.GREEN_BOLD + "- add");
        System.out.println(ConsoleColors.RED + "- remove");
        System.out.println(ConsoleColors.BLUE + "- list");
        System.out.println(ConsoleColors.RESET + "- exit");
        System.out.println();
        System.out.println("Please select an option");
    }

    //motoda do wczytania wszystich zadań w pliku
    public static String[][] loadFromFile(String fileName) {

        Path dir = Paths.get(fileName);
        if (!Files.exists(dir)) {
            System.out.println("File not exist");
            System.exit(0);
        }

        String[][] array = new String[0][0];

        try {
            File file = new File(fileName);
            int i = 0;
            String line;
            String[] splited;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line == null) {
                    System.out.println("Blank list");
                } else {
                    splited = line.split(",");
                    array = Arrays.copyOf(array, array.length + 1);
                    array[array.length - 1] = new String[splited.length];

                    System.arraycopy(splited, 0, array[i], 0, splited.length);
                    i = i + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }



    //metoda do wyświetlenia aktualnej listy zadań
    public static void printArray(String[][] Array) {

        if (Array == null) {
            System.out.println("Blank list");
        } else {
            for (int i = 0; i < Array.length; i++) {
                System.out.print(i + " : ");
                for (int j = 0; j < Array[i].length; j++) {
                    System.out.print(Array[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    //dodawanie kolejnego zadania do listy
    private static void addTask() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scanner.nextLine();

        if (tasks == null) {
            tasks = new String[0][0];
        }

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = isImportant;

    }

    //sprawdzenie czy podana liczba jest >= zero

    public static boolean isNumberGreatEqualZero(String input) {
        if (NumberUtils.isCreatable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    //wczytanie z konsoli numeru zadania do usunięcia
    public static int getNubmer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove");

        String n = scanner.nextLine();
        while (!isNumberGreatEqualZero(n)) {
            System.out.println("Incorrect argument");
            scanner.nextLine();
        }
        return Integer.parseInt(n);
    }

    //usuniecie zadania z listy
    private static void removeTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist");
        }
    }

    //zapisanie aktualnej listy zadań do pliku
    public static void saveTabToFile(String fileName, String[][] tab) {
        Path dir = Paths.get(fileName);

        if (tab == null) {
            System.out.println("Nothing to save");
        } else {
            String[] lines = new String[tasks.length];
            for (int i = 0; i < tab.length; i++) {
                lines[i] = String.join(",", tab[i]);
            }

            try {
                Files.write(dir, Arrays.asList(lines));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Saved");
        }
    }
}
