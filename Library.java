import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Collection;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.io.File;

public class Library {

    Map<Book, String> booksInSystem = new HashMap<Book, String>();
    ArrayList<Member> membersInSystem = new ArrayList<Member>();
    int memberNumberTrack = 100000;
    public static final String HELP_STRING = "EXIT ends the library process\n"
                        + "COMMANDS outputs this help string\n\n"
                        + "LIST ALL [LONG] outputs either the short or long string for all books\n"
                        + "LIST AVAILABLE [LONG] outputs either the short of long string for all available books\n"
                        + "NUMBER COPIES outputs the number of copies of each book\n"
                        + "LIST GENRES outputs the name of every genre in the system\n"
                        + "LIST AUTHORS outputs the name of every author in the system\n\n"
                        + "GENRE <genre> outputs the short string of every book with the specified genre\n"
                        + "AUTHOR <author> outputs the short string of every book by the specified author\n\n"
                        + "BOOK <serialNumber> [LONG] outputs either the short or long string for the specified book\n"
                        + "BOOK HISTORY <serialNumber> outputs the rental history of the specified book\n\n"
                        + "MEMBER <memberNumber> outputs the information of the specified member\n"
                        + "MEMBER BOOKS <memberNumber> outputs the books currently rented by the specified member\n"
                        + "MEMBER HISTORY <memberNumber> outputs the rental history of the specified member\n\n"
                        + "RENT <memberNumber> <serialNumber> loans out the specified book to the given member\n"
                        + "RELINQUISH <memberNumber> <serialNumber> returns the specified book from the member\n"
                        + "RELINQUISH ALL <memberNumber> returns all books rented by the specified member\n\n"
                        + "ADD MEMBER <name> adds a member to the system\n"
                        + "ADD BOOK <filename> <serialNumber> adds a book to the system\n\n"
                        + "ADD COLLECTION <filename> adds a collection of books to the system\n"
                        + "SAVE COLLECTION <filename> saves the system to a csv file\n\n"
                        + "COMMON <memberNumber1> <memberNumber2> ... outputs the common books in members\' history";

    public Library() {
        this.booksInSystem = booksInSystem;
        this.membersInSystem = membersInSystem;
    }

    public void getAllBooks(boolean fullString) {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        ArrayList<Integer> serialNums = new ArrayList<Integer>();
        Map<Integer, Book> bookMap = new HashMap<Integer, Book>();
        for (Book book : booksInSystem.keySet()) {
            int serialNum = Integer.parseInt(book.getSerialNumber());
            serialNums.add(serialNum);
            bookMap.put(serialNum, book);
        }
        Collections.sort(serialNums);
        boolean isShort = false;
        for (Integer num : serialNums) {
            Book bookItem = bookMap.get(num);
            if (fullString == false) {
                isShort = true;
                System.out.println(bookItem.shortString());
            } else {
                System.out.println(bookItem.longString() + "\n");
            }
        }
        if (isShort == true) {
            System.out.println();
        }
    }

    public void getAvailableBooks(boolean fullString) {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        ArrayList<Integer> serialNums = new ArrayList<Integer>();
        Map<Integer, Book> bookMap = new HashMap<Integer, Book>();
        for (Book book : booksInSystem.keySet()) {
            int serialNum = Integer.parseInt(book.getSerialNumber());
            serialNums.add(serialNum);
            bookMap.put(serialNum, book);
        }
        Collections.sort(serialNums);
        boolean isShort = false;
        int availableBooks = 0;
        for (Integer num : serialNums) {
            Book bookItem = bookMap.get(num);
            if (bookItem.isRented() == false) {
                availableBooks += 1;
                if (fullString == false) {
                    isShort = true;
                    System.out.println(bookItem.shortString());
                } else {
                    System.out.println(bookItem.longString() + "\n");
                }
            }
        }
        if (isShort == true) {
            System.out.println();
        }
        if (availableBooks == 0 && booksInSystem.size() > 0) {
            System.out.println("No books available.\n");
            return;
        }
    }

    public void getCopies() {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        Map<String, Integer> copies = new HashMap<String, Integer>();
        ArrayList<String> shortStrs = new ArrayList<String>();
        for (Book book : booksInSystem.keySet()) {
            int numCopies = 0;
            String targetBook = book.shortString();
            for (Book book1 : booksInSystem.keySet()) {
                if (book1.shortString().equals(targetBook)) {
                    numCopies += 1;
                    if (shortStrs.contains(book1.shortString()) == false) {
                        shortStrs.add(book1.shortString());
                    }
                }
            }
            copies.put(book.shortString(), numCopies);
        }
        Collections.sort(shortStrs);
        for (String shortStr : shortStrs) {
            System.out.println(shortStr + ": " + copies.get(shortStr));
        }
        System.out.println();
    }

    public void getGenres() {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        ArrayList<String> genres = new ArrayList<String>();
        for (Book book : booksInSystem.keySet()) {
            if (genres.contains(book.getGenre()) == false) {
                genres.add(book.getGenre());
            }
        }
        Collections.sort(genres);
        for (String genre : genres) {
            System.out.println(genre);
        }
        System.out.println();
    }

    public void getAuthors() {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        ArrayList<String> authors = new ArrayList<String>();
        for (Book book : booksInSystem.keySet()) {
            if (! authors.contains(book.getAuthor())) {
                authors.add(book.getAuthor());
            }
        }
        Collections.sort(authors);
        for (String author : authors) {
            System.out.println(author);
        }
        System.out.println();
    }

    public void getBooksByGenre(String genre) {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        Map<Integer, Book> bookList = new HashMap<Integer, Book>();
        ArrayList<Integer> serialNums = new ArrayList<Integer>();
        for (Book book : booksInSystem.keySet()) {
            if (book.getGenre().toUpperCase().equals(genre.toUpperCase())) {
                int serialNum = Integer.parseInt(book.getSerialNumber());
                serialNums.add(serialNum);
                bookList.put(serialNum, book);
            }
        }
        if (bookList.size() == 0 && booksInSystem.size() > 0) {
            System.out.println("No books with genre " + genre + ".\n");
            return;
        }
        Collections.sort(serialNums);
        for (Integer num : serialNums) {
            Book bookItem = bookList.get(num);
            System.out.println(bookItem.shortString());
        }
        System.out.println();
    }

    public void getBooksByAuthor(String author) {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        Map<Integer, Book> bookList = new HashMap<Integer, Book>();
        ArrayList<Integer> serialNums = new ArrayList<Integer>();
        for (Book book : booksInSystem.keySet()) {
            if (book.getAuthor().toLowerCase().equals(author.toLowerCase())) {
                int serialNum = Integer.parseInt(book.getSerialNumber());
                serialNums.add(serialNum);
                bookList.put(serialNum, book);
            }
        }
        if (bookList.size() == 0 && booksInSystem.size() > 0) {
            System.out.println("No books by " + author + ".\n");
            return;
        }
        Collections.sort(serialNums);
        for (Integer num : serialNums) {
            Book bookItem = bookList.get(num);
            System.out.println(bookItem.shortString());
        }
        System.out.println();
    }

    public void getBook(String serialNumber, boolean fullString) {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        int matches = 0;
        for (Book book : booksInSystem.keySet()) {
            if(book.getSerialNumber().equals(serialNumber)) {
                matches += 1;
                if (fullString == false) {
                    System.out.println(book.shortString());
                    System.out.println();
                } else {
                    System.out.println(book.longString());
                    System.out.println();
                }
            }
        }
        if (matches == 0) {
            System.out.println("No such book in system.\n");
            return;
        }
    }

    public void bookHistory(String serialNumber) {
        boolean exists = false;
        for (Book book : booksInSystem.keySet()) {
            if (book.serialNumber.equals(serialNumber)) {
                exists = true;
                if (book.renterHistory().size() == 0) {
                    System.out.println("No rental history.\n");
                } else {
                    for (Member member : book.renterHistory()) {
                        System.out.println(member.memberNumber);
                    }
                    System.out.println();
                }
            }
        }
        if (exists == false) {
            System.out.println("No such book in system.\n");
            return;
        }  
    }

    public void addBook(String bookFile, String serialNumber) {
        if (booksInSystem.containsValue(serialNumber)) {
            System.out.println("Book already exists in system.\n");
            return;
        } else {
            ArrayList<String> data = new ArrayList<String>();
            try (Scanner scan = new Scanner(new File(bookFile))) {
                try {
                    scan.nextLine();
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        for (String elem : line.split(",")) {
                            data.add(elem);
                        }
                    }
                }
                catch (NoSuchElementException e) {
                    System.out.println("No such book in file.\n");
                    return;
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("No such file.\n");
                return;
            }  
            boolean exists = false;
            for (String elem : data) {
                if (elem.equals(serialNumber)) {
                    exists = true;
                    int index = data.indexOf(elem);
                    Book newBook = new Book(data.get(index + 1), data.get(index + 2), data.get(index + 3), data.get(index));
                    booksInSystem.put(newBook, newBook.serialNumber);
                    System.out.println("Successfully added: " + newBook.shortString() + ".\n");
                }
            }
            if (exists == false) {
                System.out.println("No such book in file.\n");
                return;
            }
        }   
    }

    public void rentBook(String memberNumber, String serialNumber) {
        Member targetMember = null;
        Book targetBook = null;
        boolean memberExists = false;
        boolean bookExists = false;
        if (membersInSystem.size() == 0) {
            System.out.println("No members in system.\n");
            return;
        }
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        for (Member member : membersInSystem) {
            if (member.memberNumber.equals(memberNumber)) {
                targetMember = member;
                memberExists = true;
            } 
        }
        for (Book book : booksInSystem.keySet()) {
            if (book.serialNumber.equals(serialNumber)) {
                targetBook = book;
                bookExists = true; 
            }
        }
        if (memberExists == false) {
            System.out.println("No such member in system.\n");
            return;
        }
        if (bookExists == false) {
            System.out.println("No such book in system.\n");
            return;
        }
        else if (targetBook.isRented() == true) {
            System.out.println("Book is currently unavailable.\n");
        }
        else {
            targetMember.rent(targetBook);
            System.out.println("Success.\n");
        }

    }

    public void relinquishBook(String memberNumber, String serialNumber) {
        Member targetMember = null;
        Book targetBook = null;
        boolean memberExists = false;
        boolean bookExists = false;
        if (membersInSystem.size() == 0) {
            System.out.println("No members in system.\n");
            return;
        }
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        for (Member member : membersInSystem) {
            if (member.memberNumber.equals(memberNumber)) {
                targetMember = member;
                memberExists = true;
            } 
        }
        for (Book book : booksInSystem.keySet()) {
            if (book.serialNumber.equals(serialNumber)) {
                targetBook = book;
                bookExists = true; 
            }
        }
        if (memberExists == false) {
            System.out.println("No such member in system.\n");
            return;
        }
        if (bookExists == false) {
            System.out.println("No such book in system.\n");
            return;
        }
        if (targetBook.currentRenter == null) {
            System.out.println("Unable to return book.\n");
            return;
        }
        else if (targetBook.currentRenter.equals(targetMember) == false) {
            System.out.println("Unable to return book.\n");
            return;
        }
        else {
            targetMember.relinquish(targetBook);
            System.out.println("Success.\n");
        }

    }

    public void relinquishAll(String memberNumber) {
        if (membersInSystem.size() == 0) {
            System.out.println("No members in system.\n");
            return;
        }
        boolean exists = false;
        for (Member member : membersInSystem) {
            if (member.getMemberNumber().equals(memberNumber)) {
                exists = true;
                member.relinquishAll();
                System.out.println("Success.\n");
                return;
            }
        }
        if (exists == false) {
            System.out.println("No such member in system.\n");
        }
    }

    public void getMember(String memberNumber) {
        boolean exists = false;
        if (membersInSystem.size() == 0) {
            System.out.println("No members in system.\n");
            return;
        }
        for (Member member : membersInSystem) {
            if (member.memberNumber.equals(memberNumber)) {
                exists = true;
                System.out.println(member.memberNumber + ": " + member.name + "\n");
            }
        }
        if (exists == false) {
            System.out.println("No such member in system.\n");
        }

    }

    public void getMemberBooks(String memberNumber) {
        boolean exists = false;
        if (membersInSystem.size() == 0) {
            System.out.println("No members in system.\n");
            return;
        }
        for (Member member : membersInSystem) {
            if (member.memberNumber.equals(memberNumber)) {
                exists = true;
                if (member.currentlyRenting.size() == 0) {
                    System.out.println("Member not currently renting.\n");
                    return;
                } else {
                    for (Book book : member.currentlyRenting) {
                        System.out.println(book.shortString());
                    }
                    System.out.println();
                }
            }
        }
        if (exists == false) {
            System.out.println("No such member in system.\n");
            return;
        }
    }

    public void memberRentalHistory(String memberNumber) {
        boolean exists = false;
        if (membersInSystem.size() == 0) {
            System.out.println("No members in system.\n");
            return;
        }
        for (Member member : membersInSystem) {
            if (member.getMemberNumber().equals(memberNumber)) {
                exists = true;
                if (member.rentalHistoryMember.size() == 0) {
                    System.out.println("No rental history for member.\n");
                    return;
                } else {
                    for (Book book : member.rentalHistoryMember) {
                        System.out.println(book.shortString());
                    }
                    System.out.println();
                }
            }
        }
        if (exists == false) {
            System.out.println("No such member in system.\n");
            return;
        }
    }

    public void addMember(String name) {
        String memNum = Integer.toString(this.memberNumberTrack);
        Member newMember = new Member(name, memNum);
        membersInSystem.add(newMember);
        this.memberNumberTrack += 1;
        System.out.println("Success.\n");
    }

    public void saveCollection(String filename) {
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }
        ArrayList<Integer> serialNums = new ArrayList<Integer>();
        Map<Integer, Book> bookMap = new HashMap<Integer, Book>();
        for (Book book : booksInSystem.keySet()) {
            int serialNum = Integer.parseInt(book.getSerialNumber());
            serialNums.add(serialNum);
            bookMap.put(serialNum, book);
        }
        Collections.sort(serialNums);
        ArrayList<Book> listOfBooks = new ArrayList<Book>();
        for (Integer num : serialNums) {
            Book bookItem = bookMap.get(num);
            listOfBooks.add(bookItem);
        }
        Book.saveBookCollection(filename, listOfBooks);
        System.out.println("Success.\n");
    }

    public void addCollection(String filename) {
        int check = booksInSystem.size();
        List<Book> bookList = Book.readBookCollection(filename);
        if (bookList == null) {
            System.out.println("No such collection.\n");
            return;
        }
        for (Book book : bookList) {
            if (booksInSystem.containsValue(book.serialNumber)) {
                continue; 
            } else {
                booksInSystem.put(book, book.serialNumber);
            }
        }
        if (booksInSystem.size() == check) {
            System.out.println("No books have been added to the system.\n");
        }
        else if (booksInSystem.size() > check) {
            int diff = booksInSystem.size() - check;
            System.out.println(diff + " books successfully added.\n");
        }
    }

    public void common(String[] memberNumbers) {
        if (membersInSystem.size() == 0) {
            System.out.println("No members in system.\n");
            return;
        }
        if (booksInSystem.size() == 0) {
            System.out.println("No books in system.\n");
            return;
        }   
        Map<String, Member> memsMap = new HashMap<String, Member>();
        ArrayList<Member> mems = new ArrayList<Member>();
        for (Member member : membersInSystem) {
            memsMap.put(member.memberNumber, member);
        }
        for (String memNum : memberNumbers) {
            if (memsMap.containsKey(memNum) == false) {
                System.out.println("No such member in system.\n");
                return;
            } 
            else if (mems.contains(memsMap.get(memNum))) {
                System.out.println("Duplicate members provided.\n");
                return;
            }
            else {
                mems.add(memsMap.get(memNum));
            }
        }
        Member[] memsArr = new Member[mems.size()];
        int i = 0;
        while (i < mems.size()) {
            memsArr[i] = mems.get(i);
            i++;
        }
        List<Book> commBooks = Member.commonBooks(memsArr);
        if (commBooks.size() == 0) {
            System.out.println("No common books.");
        } else {
            for (Book book : commBooks) {
                System.out.println(book.shortString());
            }
        }
        System.out.println();

    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Library libraryObj = new Library();

        while (true) {
            System.out.print("user: ");
            String command = scan.nextLine();
            if (command.toUpperCase().equals("EXIT")) {
                System.out.println("Ending Library process.");
                return;
            } 
            else if (command.toUpperCase().equals("COMMANDS")) {
                System.out.println(HELP_STRING);
                System.out.println();
            }
            else if (command.toUpperCase().equals("LIST ALL")) {
                libraryObj.getAllBooks(false);
            }
            else if (command.toUpperCase().equals("LIST ALL LONG")) {
                libraryObj.getAllBooks(true);
            }
            else if (command.toUpperCase().equals("LIST AVAILABLE")) {
                libraryObj.getAvailableBooks(false);
            }
            else if (command.toUpperCase().equals("LIST AVAILABLE LONG")) {
                libraryObj.getAvailableBooks(true);
            }
            else if (command.toUpperCase().equals("NUMBER COPIES")) {
                libraryObj.getCopies();
            }
            else if (command.toUpperCase().equals("LIST GENRES")) {
                libraryObj.getGenres();
            }
            else if (command.toUpperCase().equals("LIST AUTHORS")) {
                libraryObj.getAuthors();
            }
            String[] commandSplit = command.split(" ");

            if (commandSplit[0].toUpperCase().equals("GENRE")) {
                if (commandSplit.length == 2) {
                    libraryObj.getBooksByGenre(commandSplit[1]);
                } else {
                    int i = 2;
                    String genre = "";
                    genre += commandSplit[1];
                    genre += " ";
                    while (i < commandSplit.length - 1) {
                        genre += commandSplit[i];
                        genre += " ";
                        i++;
                    }
                    genre += commandSplit[commandSplit.length - 1];
                    libraryObj.getBooksByGenre(genre);
                }
            }
            if (commandSplit[0].toUpperCase().equals("AUTHOR")) {
                if (commandSplit.length == 2) {
                    libraryObj.getBooksByAuthor(commandSplit[1]);
                } else {
                    int i = 2;
                    String name = "";
                    name += commandSplit[1];
                    name += " ";
                    while (i < commandSplit.length - 1) {
                        name += commandSplit[i];
                        name += " ";
                        i++;
                    }
                    name += commandSplit[commandSplit.length - 1];
                    libraryObj.getBooksByAuthor(name);
                }
            }
            if (commandSplit[0].toUpperCase().equals("BOOK") && commandSplit.length == 2) {
                libraryObj.getBook(commandSplit[1], false);
            }
            if (commandSplit[0].toUpperCase().equals("BOOK") && commandSplit.length == 3 
                            && ! commandSplit[1].toUpperCase().equals("HISTORY")) {
                libraryObj.getBook(commandSplit[1], true);
            }
            if (commandSplit[0].toUpperCase().equals("BOOK") 
                            && commandSplit[1].toUpperCase().equals("HISTORY")) {
                libraryObj.bookHistory(commandSplit[2]);
            }
            if (commandSplit[0].toUpperCase().equals("MEMBER") 
                            && commandSplit.length == 2) {
                libraryObj.getMember(commandSplit[1]);
            }
            if (commandSplit[0].toUpperCase().equals("MEMBER") 
                            && commandSplit[1].toUpperCase().equals("BOOKS")) {
                libraryObj.getMemberBooks(commandSplit[2]);
            }
            if (commandSplit[0].toUpperCase().equals("MEMBER") 
                            && commandSplit[1].toUpperCase().equals("HISTORY")) {
                libraryObj.memberRentalHistory(commandSplit[2]);
            }
            if (commandSplit[0].toUpperCase().equals("RENT")) {
                libraryObj.rentBook(commandSplit[1], commandSplit[2]);
            }
            if (commandSplit[0].toUpperCase().equals("RELINQUISH") 
                            && ! commandSplit[1].toUpperCase().equals("ALL")) {
                libraryObj.relinquishBook(commandSplit[1], commandSplit[2]);
            }
            if (commandSplit[0].toUpperCase().equals("RELINQUISH") 
                            && commandSplit[1].toUpperCase().equals("ALL")) {
                libraryObj.relinquishAll(commandSplit[2]);
            }
            if (commandSplit[0].toUpperCase().equals("ADD") 
                            && commandSplit[1].toUpperCase().equals("MEMBER")) {
                if (commandSplit.length == 3) {
                    libraryObj.addMember(commandSplit[2]);
                } else {
                    int i = 3;
                    String name = "";
                    name += commandSplit[2];
                    name += " ";
                    while (i < commandSplit.length - 1) {
                        name += commandSplit[i];
                        name += " ";
                        i++;
                    }
                    name += commandSplit[commandSplit.length - 1];
                    libraryObj.addMember(name);
                }
            }

            if (commandSplit[0].toUpperCase().equals("ADD") 
                            && commandSplit[1].toUpperCase().equals("BOOK")) {
                libraryObj.addBook(commandSplit[2], commandSplit[3]);
            }
            if (commandSplit[0].toUpperCase().equals("ADD") 
                            && commandSplit[1].toUpperCase().equals("COLLECTION")) {
                libraryObj.addCollection(commandSplit[2]);
            }
            if (commandSplit[0].toUpperCase().equals("SAVE") 
                            && commandSplit[1].toUpperCase().equals("COLLECTION")) {
                libraryObj.saveCollection(commandSplit[2]);
            }
            if (commandSplit[0].toUpperCase().equals("COMMON")) {
                String[] memberNumbers = new String[commandSplit.length -1];
                int i = 1;
                int j = 0;
                while (j < memberNumbers.length) {
                    memberNumbers[j] = commandSplit[i];
                    j++;
                    i++;
                }
                libraryObj.common(memberNumbers);
            }
        }
    }
}