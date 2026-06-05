package rvt.sqlite;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Kļūda: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n--- IZVELNE ---");
            System.out.println("1 - Pievienot kategoriju");
            System.out.println("2 - Pievienot produktu");
            System.out.println("3 - Paradit visas kategorijas");
            System.out.println("4 - Paradit visus produktus");
            System.out.println("5 - Meklet produktus pec kategorijas");
            System.out.println("0 - Iziet");
            System.out.print("Izvele: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> addCategory();
                    case "2" -> addProduct();
                    case "3" -> showCategories();
                    case "4" -> showProducts();
                    case "5" -> searchProducts();
                    case "0" -> {
                        System.out.println("Uz redzēšanos!");
                        DatabaseConnection.closeConnection();
                        return;
                    }
                    default -> System.out.println("Nepareiza izvēle!");
                }
            } catch (SQLException e) {
                System.out.println("Datubāzes kļūda: " + e.getMessage());
            }
        }
    }

    static void addCategory() throws SQLException {
        System.out.print("Kategorijas nosaukums: ");
        String name = scanner.nextLine();
        if (name.isBlank()) { System.out.println("Nosaukums nedrīkst būt tukšs!"); return; }
        if (Category.add(name)) System.out.println("Kategorija pievienota!");
        else System.out.println("Neizdevās pievienot.");
    }

    static void addProduct() throws SQLException {
        List<Category> cats = Category.getAll();
        if (cats.isEmpty()) { System.out.println("Vispirms pievieno kategoriju!"); return; }

        System.out.println("Kategorijas:");
        cats.forEach(System.out::println);

        System.out.print("Produkta nosaukums: ");
        String name = scanner.nextLine();
        if (name.isBlank()) { System.out.println("Nosaukums nedrīkst būt tukšs!"); return; }

        System.out.print("Cena: ");
        double price;
        try { price = Double.parseDouble(scanner.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Nepareiza cena!"); return; }
        if (price <= 0) { System.out.println("Cenai jābūt > 0!"); return; }

        System.out.print("Kategorijas ID: ");
        int catId;
        try { catId = Integer.parseInt(scanner.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Nepareizs ID!"); return; }
        if (!Category.exists(catId)) { System.out.println("Kategorija neeksistē!"); return; }

        if (Product.add(name, price, catId)) System.out.println("Produkts pievienots!");
        else System.out.println("Neizdevās pievienot.");
    }

    static void showCategories() throws SQLException {
        List<Category> list = Category.getAll();
        if (list.isEmpty()) { System.out.println("Nav kategoriju."); return; }
        System.out.println("\nID | Nosaukums");
        System.out.println("-------------------");
        list.forEach(System.out::println);
    }

    static void showProducts() throws SQLException {
        List<Product> list = Product.getAll();
        if (list.isEmpty()) { System.out.println("Nav produktu."); return; }
        System.out.println("\nID | Nosaukums | Cena | Kategorija");
        System.out.println("------------------------------------");
        list.forEach(System.out::println);
    }

    static void searchProducts() throws SQLException {
        System.out.print("Ievadi kategorijas ID vai nosaukumu: ");
        String input = scanner.nextLine();
        List<Product> list = Product.searchByCategory(input);
        if (list.isEmpty()) { System.out.println("Nav atrasts neviens produkts."); return; }
        System.out.println("\nAtrasti produkti:");
        list.forEach(System.out::println);
    }
}

