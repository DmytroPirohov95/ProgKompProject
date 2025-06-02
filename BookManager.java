package service;

import model.Book;

import java.util.*;

/**
 * Klasa zarządzająca książkami. Zawiera operacje CRUD oraz statystyki.
 */
public class BookManager {
    private List<Book> books = new ArrayList<>();
    private Map<String, Integer> operationCounter = new HashMap<>();

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        incrementCounter("dodano");
    }

    public void deleteBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
        incrementCounter("usunieto");
    }

    public List<Book> findBooks(String keyword) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }
        incrementCounter("wyszukano");
        return results;
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("Brak książek do wyświetlenia.");
            return;
        }

        System.out.printf("%-30s %-20s %-4s%n", "Tytuł", "Autor", "Rok");
        System.out.println("----------------------------------------------------------");
        for (Book book : books) {
            System.out.printf("%-30s %-20s %-4d%n", book.getTitle(), book.getAuthor(), book.getYear());
        }
    }

    public void printStats() {
        System.out.println("Statystyki użytkownika:");
        for (Map.Entry<String, Integer> entry : operationCounter.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void incrementCounter(String operation) {
        operationCounter.put(operation, operationCounter.getOrDefault(operation, 0) + 1);
    }
}
