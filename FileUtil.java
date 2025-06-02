package util;

import model.Book;

import java.io.*;
import java.util.*;

/**
 * Klasa narzędziowa do operacji na plikach.
 */
public class FileUtil {
    public static List<Book> readBooks(String filename) throws IOException {
        List<Book> books = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine(); // pomiń nagłówek
        while ((line = reader.readLine()) != null) {
            // usuń potencjalne "" lub inne niechciane znaki
            line = line.trim().replace("\"", "");
            String[] parts = line.split(";");
            if (parts.length == 3) {
                try {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    books.add(new Book(title, author, year));
                } catch (NumberFormatException e) {
                    System.out.println("Błędny format roku w linii: " + line);
                }
            } else {
                System.out.println("Nieprawidłowy format linii: " + line);
            }
        }
        reader.close();
        return books;
    }

    public static void saveBooks(String filename, List<Book> books) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("Tytuł;Autor;Rok\n");
        for (Book book : books) {
            writer.write(book.getTitle() + ";" + book.getAuthor() + ";" + book.getYear() + "\n");
        }
        writer.close();
    }
}
