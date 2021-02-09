import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.io.File;

public class Member {
    String name;
    String memberNumber;
    ArrayList<Book> currentlyRenting = new ArrayList<Book>();
    ArrayList<Book> rentalHistoryMember = new ArrayList<Book>();
    
    public Member(String name, String memberNumber) {
        this.name = name;
        this.memberNumber = memberNumber;
        this.currentlyRenting = currentlyRenting;
        this.rentalHistoryMember = rentalHistoryMember;
    }

    public String getName() {
        return this.name;
    }

    public String getMemberNumber() {
        return this.memberNumber;
    }

    public boolean rent(Book book) {
        if (book == null || book.currentRenter != null) {
            return false;
        }
        this.currentlyRenting.add(book);
        book.rent(this);
        return true;
    }

    public boolean relinquish(Book book) {
        if (book == null || currentlyRenting.contains(book) == false) {
            return false;
        }

        this.rentalHistoryMember.add(book);
        this.currentlyRenting.remove(book);
        book.relinquish(this);
        return true;
    }

    public void relinquishAll() {
        ArrayList<Book> bookList = new ArrayList<Book>();
        for (Book book : currentlyRenting) {
            bookList.add(book);
        }
        for (Book book : bookList) {
            book.relinquish(this);
            this.relinquish(book);
        }
    }

    public List<Book> history() {
        ArrayList<Book> historyList = new ArrayList<Book>();
        for (Book book : rentalHistoryMember) {
            if (! currentlyRenting.contains(book)) {
                historyList.add(book);
            }
        }
        return historyList;
    }

    public List<Book> renting() {
        return this.currentlyRenting;
    }

    public static List<Book> commonBooks(Member[] members) {
        if (members == null || members.length == 0) {
            return null;
        }
        for (Member member : members) {
            if (member == null) {
                return null;
            }
        }

        List<Book> list = new ArrayList<Book>();
        if (members.length == 1) {
            return members[0].rentalHistoryMember;
        }
        Map<Integer, Book> bookList = new HashMap<Integer, Book>();
        Map<Integer, Book> bookList2 = new HashMap<Integer, Book>();
        ArrayList<Integer> serialNums = new ArrayList<Integer>();

        for (Book book : members[0].rentalHistoryMember) {
            int serialNum = Integer.parseInt(book.getSerialNumber());
            serialNums.add(serialNum);
            bookList.put(serialNum, book);
            bookList2.put(serialNum, book);
        }
        for (Book book : bookList.values()) {
            int i = 1;
            while (i < members.length) {
                if (members[i].rentalHistoryMember.contains(book) == false) {
                    int serialNum = Integer.parseInt(book.getSerialNumber());
                    bookList2.remove(serialNum);
                    int index = serialNums.indexOf(serialNum);
                    serialNums.remove(index);
                    break;
                }
                i++;
            }
        }
        Collections.sort(serialNums);
        for (int num : serialNums) {
            list.add(bookList2.get(num));
        }
        int i = 0;
        int j = 0;
        while(i < list.size()){
            while(j < list.size()){
                if (list.get(j) == list.get(i) && i != j){
                    list.remove(j);
                }
                j++;
            }
            j=0;
            i++;
        }
        return list;
    }
}