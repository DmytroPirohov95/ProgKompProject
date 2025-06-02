package main;

import model.Book;
import service.BookManager;
import util.FileUtil;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Główna klasa aplikacji do zarządzania książkami.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookManager manager = new BookManager();
        Instant start = Instant.now();

        while (true) {
            System.out.println("\n==== MENU ====\n" +
                    "1. Wczytaj książki z pliku\n" +
                    "2. Dodaj książkę\n" +
                    "3. Usuń książkę\n" +
                    "4. Szukaj książki\n" +
                    "5. Wyświetl wszystkie książki\n" +
                    "6. Zapisz dane do pliku\n" +
                    "7. Pokaż statystyki\n" +
                    "8. Zakończ");
            System.out.print("Wybierz opcję: ");

            int option = -1;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawny format. Podaj liczbę.");
                continue;
            }

            switch (option) {
                case 1:
                    System.out.print("Podaj ścieżkę do pliku CSV: ");
                    String path = scanner.nextLine();
                    try {
                        List<Book> books = FileUtil.readBooks(path);
                        manager.setBooks(books);
                        System.out.println("Wczytano " + books.size() + " książek.");
                    } catch (IOException e) {
                        System.out.println("Błąd podczas odczytu pliku: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Tytuł: ");
                    String title = scanner.nextLine();
                    System.out.print("Autor: ");
                    String author = scanner.nextLine();
                    System.out.print("Rok wydania: ");
                    int year;
                    try {
                        year = Integer.parseInt(scanner.nextLine());
                        boolean added = manager.addBook(new Book(title, author, year));
                        if (added) {
                            System.out.println("Dodano książkę.");
                        } else {
                            System.out.println("Taka książka już istnieje.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Niepoprawny rok.");
                    }
                    break;
                case 3:
                    System.out.print("Podaj tytuł książki do usunięcia: ");
                    String titleToDelete = scanner.nextLine();
                    boolean deleted = manager.deleteBook(titleToDelete);
                    if (deleted) {
                        System.out.println("Książka została usunięta.");
                    } else {
                        System.out.println("Nie znaleziono książki o podanym tytule.");
                    }
                    break;
                case 4:
                    System.out.print("Podaj frazę do wyszukania (pełne słowo lub początek tytułu/autor): ");
                    String keyword = scanner.nextLine().trim();
                    List<Book> results = manager.findBooks(keyword);
                    if (results.isEmpty()) {
                        System.out.println("Nie znaleziono książek pasujących do podanej frazy.");
                    } else {
                        System.out.println("Znalezione książki:");
                        for (Book b : results) {
                            System.out.printf("%-30s %-20s %-4d%n", b.getTitle(), b.getAuthor(), b.getYear());
                        }
                    }
                    break;
                case 5:
                    List<Book> allBooks = manager.getBooks();
                    if (allBooks.isEmpty()) {
                        System.out.println("Brak książek w bazie.");
                    } else {
       
                        allBooks.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER)
                                .thenComparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER));
                        
                        System.out.printf("%-30s %-20s %-4s%n", "Tytuł", "Autor", "Rok");
                       
                        for (Book b : allBooks) {
                            System.out.printf("%-30s %-20s %-4d%n", b.getTitle(), b.getAuthor(), b.getYear());
                        }
                    }
                    break;
                case 6:
                    try {
                        FileUtil.saveBooks("books.csv", manager.getBooks());
                        System.out.println("Zapisano książki do pliku books.csv");
                    } catch (IOException e) {
                        System.out.println("Błąd zapisu: " + e.getMessage());
                    }
                    break;
                case 7:
                    manager.printStats();
                    break;
                case 8:
                    Instant end = Instant.now();
                    long seconds = Duration.between(start, end).toSeconds();
                    System.out.println("Czas działania programu: " + seconds + " sekundy.");
                    return;
                default:
                    System.out.println("Nieznana opcja.");
            }
        }
    }
}
