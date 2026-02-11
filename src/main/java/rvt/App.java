package rvt;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ToDoList list = new ToDoList();

        while (true) {
            System.out.print("Command: ");
            String command = scanner.nextLine();

            if (command.equals("stop")) {
                break;
            }
            
            if (command.equals("add")) {
                System.out.print("Task: ");
                String task = scanner.nextLine();
                list.add(task);
            }

            if (command.equals("list")) {
                list.print();
            }

            if (command.equals("remove")) {
                System.out.print("ID: ");
                int id = Integer.valueOf(scanner.nextLine());
                list.remove(id);
            }
        }
    }
}


