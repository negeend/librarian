import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Collection;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;

public class Book {

    String title;
    String author;
    String genre;
    String serialNumber;
    Member currentRenter = null;
    ArrayList<Member> rentalHistoryBook = new ArrayList<Member>();


    public Book(String title, 
                String author, 
                String genre, 
                String serialNumber) 
        {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.serialNumber = serialNumber;
        this.currentRenter = currentRenter;
        this.rentalHistoryBook = rentalHistoryBook;

    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String longString() {
        if (this.currentRenter == null) {
            String longStr = this.serialNumber + ": " + this.title + " (" + this.author;
            longStr += ", " + this.genre + ")\nCurrently available.";
            return longStr;
        } else {
            String longStr = this.serialNumber + ": " + this.title + " (" + this.author + ", ";
            longStr += this.genre + ")\nRented by: " + this.currentRenter.memberNumber + ".";
            return longStr;
        }
    }

    public String shortString() {
        String shortStr = this.title + " (" + this.author + ")";
        return shortStr;
    }

    public List<Member> renterHistory() {
        return this.rentalHistoryBook;
    }

    public boolean isRented() {
        if (this.currentRenter == null) {
            return false;
        }
        return true;
    }

    public boolean rent(Member member) {
        if (member == null || this.currentRenter != null) {
            return false;
        }
        this.currentRenter = member;
        return true;
    }

    public boolean relinquish(Member member) {
        if (member == null || this.currentRenter != member) {
            return false;
        }
        this.currentRenter = null;
        this.rentalHistoryBook.add(member);
        return true;
    }

    public static Book readBook(String filename, String serialNumber) {
        if (filename == null || serialNumber == null) {
            return null;
        }
        ArrayList<String> data = new ArrayList<String>();
        try (Scanner scan = new Scanner(new File(filename))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                for (String elem : line.split(",")) {
                    data.add(elem);
                }
            }
            boolean exists = false;
            for (String elem : data) {
                if (elem.equals(serialNumber)) {
                    exists = true;
                    int index = data.indexOf(elem);
                    Book newBook = new Book(data.get(index + 1), data.get(index + 2), data.get(index + 3), data.get(index));
                    return newBook; 
                }
            }
            if (exists == false) {
                return null;
            }
        }
        catch(FileNotFoundException e) {
            return null;
        }
        return null;  
    }

    public static List<Book> readBookCollection(String filename) {
        if (filename == null) {
            return null;
        }
        ArrayList<String> data = new ArrayList<String>();
        try (Scanner scan = new Scanner(new File(filename))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                for (String elem : line.split(",")) {
                    data.add(elem);
                }
            }
            ArrayList<Book> bookList = new ArrayList<Book>();
            int i = 4;
            while (i < data.size() - 3) {
                Book bookItem = new Book(data.get(i + 1), data.get(i + 2), data.get(i + 3), data.get(i));
                bookList.add(bookItem);
                i += 4;
            }
            return bookList;
        }
        catch (FileNotFoundException e) {
            return null;
        }
    }

    public static void saveBookCollection(String filename, Collection<Book> books) {
        if (filename == null || books == null) {
            return;
        }
        try {
            PrintWriter outputStream = new PrintWriter(filename);
            outputStream.println("serialNumber,title,author,genre");
            for (Book book : books) {
                outputStream.print(book.serialNumber + ",");
                outputStream.print(book.title + ",");
                outputStream.print(book.author + ",");
                outputStream.println(book.genre);
            }
            outputStream.close();

        } catch(FileNotFoundException e) {
            return;
        }
    }

    public static List<Book> filterAuthor(List<Book> books, String author) {
        if (books == null || author == null) {
            return null;
        }
        ArrayList<Book> filteredList1 = new ArrayList<Book>();
        ArrayList<Integer> serialNums = new ArrayList<Integer>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                filteredList1.add(book);
                int num = Integer.parseInt(book.serialNumber);
                serialNums.add(num);
            }
        }
        Collections.sort(serialNums);
        ArrayList<Book> filteredList2 = new ArrayList<Book>();
        for (int num : serialNums) {
            String serialNum = Integer.toString(num);
            for (Book book : filteredList1) {
                if (book.serialNumber.equals(serialNum)) {
                    filteredList2.add(book);
                }
            }
        }
        return filteredList2;
    }

    public static List<Book> filterGenre(List<Book> books, String genre) {
        if (books == null || genre == null) {
            return null;
        }
        ArrayList<Book> filteredList1 = new ArrayList<Book>();
        ArrayList<Integer> serialNums = new ArrayList<Integer>();
        for (Book book : books) {
            if (book.getGenre().equals(genre)) {
                filteredList1.add(book);
                int num = Integer.parseInt(book.serialNumber);
                serialNums.add(num);
            }
        }
        Collections.sort(serialNums);
        ArrayList<Book> filteredList2 = new ArrayList<Book>();
        for (int num : serialNums) {
            String serialNum = Integer.toString(num);
            for (Book book : filteredList1) {
                if (book.serialNumber.equals(serialNum)) {
                    filteredList2.add(book);
                }
            }
        }
        return filteredList2;
    }

}