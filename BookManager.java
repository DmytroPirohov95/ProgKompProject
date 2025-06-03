package service;

import model.Book;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookManager {
    private List<Book> books = new ArrayList<>();
    private int searchCount = 0;
    private int deleteCount = 0;
    private int addCount = 0; 

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean addBook(Book book) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(book.getTitle())
                    && b.getAuthor().equalsIgnoreCase(book.getAuthor())
                    && b.getYear() == book.getYear()) {
                return false;
            }
        }
        books.add(book);
        addCount++; 
        return true;
    }

    public boolean deleteBook(String title) {
        Iterator<Book> iterator = books.iterator();
        String trimmedTitle = title.trim().toLowerCase();
        while (iterator.hasNext()) {
            Book b = iterator.next();
            if (b.getTitle().trim().toLowerCase().equals(trimmedTitle)) {
                iterator.remove();
                deleteCount++;
                return true;
            }
        }
        return false;
    }

    public List<Book> findBooks(String keyword) {
        searchCount++; 
        List<Book> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(lowerKeyword) ||
                b.getAuthor().toLowerCase().contains(lowerKeyword)) {
                result.add(b);
            }
        }
        return result;
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("Brak książek w bazie.");
            return;
        }
        books.stream()
                .sorted((b1, b2) -> {
                    int cmp = b1.getTitle().compareToIgnoreCase(b2.getTitle());
                    if (cmp == 0) {
                        cmp = b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
                    }
                    return cmp;
                })
                .forEach(b ->
                        System.out.printf("%-30s %-20s %-4d%n", b.getTitle(), b.getAuthor(), b.getYear())
                );
    }

    public void printStats() {
        System.out.println("Liczba wszystkich książek: " + books.size());
        System.out.println("Liczba dodanych książek: " + addCount);
        System.out.println("Liczba szukanych książek: " + searchCount);
        System.out.println("Liczba usuniętych książek: " + deleteCount);
    }
}
