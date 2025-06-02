package service;

import model.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookManager {
    private List<Book> books = new ArrayList<>();

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
        return true;
    }

    public boolean deleteBook(String title) {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book b = iterator.next();
            if (b.getTitle().equalsIgnoreCase(title.trim())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

  
    public List<Book> findBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Book b : books) {
            String[] titleWords = b.getTitle().toLowerCase().split("\\s+");
            String[] authorWords = b.getAuthor().toLowerCase().split("\\s+");
            boolean found = false;
            for (String word : titleWords) {
                if (word.equals(lowerKeyword) || word.startsWith(lowerKeyword)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (String word : authorWords) {
                    if (word.equals(lowerKeyword) || word.startsWith(lowerKeyword)) {
                        found = true;
                        break;
                    }
                }
            }
            if (found)
                result.add(b);
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
        System.out.println("Liczba książek: " + books.size());
        
    }
}
