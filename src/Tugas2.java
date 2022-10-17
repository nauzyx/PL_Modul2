import java.util.Arrays;
import java.util.Scanner;

interface library {
    /*
        cekName, cekFrom, cekDate Function are method for check exist
        array variable which have 2 method
        find         ->  are used for users which wants to find related book
        dup          ->  are used for system to know if users add some book
                         which already exist in the array
        matchRegion  ->  are used for system to know if users input book region
                         match with registered region in the system
    */
    boolean cekName(String function, String name);
    boolean cekFrom(String funcion, String from);
    void cekDate(int date);
    void list();
}

public class Tugas2 implements library {
    Scanner input = new Scanner(System.in);
    String inName, inFrom;
    int inDate, inIndex = 1;
    public String[] book = new String[1];
    String[] place = {"Malang", "Bandung", "Surabaya"};
    public void cekDate(int date){
        if (date == 0){
            for (String s : book) {
                int parsedYear = Integer.parseInt(parseBook(s, "date"));
                if (parsedYear <= 2018) {
                    System.out.printf("%s, %s. %s\n", parseBook(s, "name"), parseBook(s, "from"), parseBook(s, "date"));
                }
            }
        } else {
            for (String s : book) {
                int parsedYear = Integer.parseInt(parseBook(s, "date"));
                if (parsedYear > 2018) {
                    System.out.printf("%s, %s. %s\n", parseBook(s, "name"), parseBook(s, "from"), parseBook(s, "date"));
                }
            }
        }
    }

    public boolean cekFrom(String function, String from){
        if (function.equals("find")) {
            for (String s : book) {
                if (from.compareTo(parseBook(s, "from")) == 0) {
                    System.out.printf("%s, %s. %s\n", parseBook(s, "name"), parseBook(s, "from"), parseBook(s, "date"));
                }
            }
        } else if (function.equals("matchRegion")){
            for (String s : place) {
                if (s.equals(from)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cekName(String function, String name){
        if (function.equals("find")){
            for (String s : book) {
                if (name.compareTo(parseBook(s, "name")) == 0) {
                    System.out.printf("%s, %s. %s\n", parseBook(s, "name"), parseBook(s, "from"), parseBook(s, "date"));
                }
            }
        } else if (function.equals("dup")){
            for (String s : book) {
                if (name.compareTo(parseBook(s, "name")) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void list(){
        System.out.printf("%-15s | %-15s | %-15s", "Name", "From", "Year");
        for (String s : book) {
            System.out.printf("\n%-15s , %-15s . %-15s", parseBook(s,"name"), parseBook(s, "from"), parseBook(s, "date"));
        }
        returnMenu();
    }

    private String parseBook(String book, String tag){
        String[] parsedBook = book.split("[,.]");
        String parsedName = parsedBook[0];
        String parsedFrom = parsedBook[1];
        String parsedDate = parsedBook[2];
        return switch (tag) {
            case "name" -> parsedName;
            case "from" -> parsedFrom;
            case "date" -> parsedDate;
            default -> "null";
        };
    }

    void findBook() {
        System.out.println("Find Book");
        System.out.println("1. Find books by Region\n2. Find books by Date");
        System.out.print("Pilih menu : ");
        int findMethod = input.nextInt();
        switch (findMethod) {
            case 1 -> {
                System.out.println("Finding Book Using Region Method");
                System.out.print("Input Region : ");
                input.nextLine();
                String findRegion = input.nextLine();
                cekFrom("find", findRegion);
            }
            case 2 -> {
                System.out.println("Finding Book Using Date Method");
                System.out.println("a. Newer ( 2018 -> Latest )\nb. Old ( 2018 -> Older )");
                input.nextLine();
                System.out.print("Pilih menu : ");
                char findDate = input.next().charAt(0);
                if (findDate == 'a'){
                    cekDate(1);
                } else {
                    cekDate(0);
                }
            }
        }
        returnMenu();
    }
    
    public void registerBook(){
        System.out.println("Register ");
        input.nextLine();
        inputName();
        inputFrom();
        inputDate();
        String bookName = (inName + "," + inFrom + "." + inDate);
        book = Arrays.copyOf(book, inIndex + 1);
        book[inIndex] = bookName;
        inIndex++;
        System.out.println("Buku berhasil ditambahkan");
        returnMenu();
    }

    public void deleteBook(){
        System.out.println("Delete Book ");
        System.out.print("Masukkan Nama Buku : ");
        input.nextLine(); String deleteIn = input.nextLine();
        String[] tempBook = new String[book.length -1];
        if (cekName("dup", deleteIn)) {
            for (int i = 0, k = 0; i < book.length; i++) {
                if (!deleteIn.equals(parseBook(book[i], "name"))) {
                    tempBook[k++] = book[i];
                }
            }
            inIndex--;
            book = Arrays.copyOf(tempBook, inIndex);
        } else {
            System.out.println("Buku Tidak Tersedia di Sistem");
        }
        returnMenu();
    }

    public void inputName(){
        System.out.print("Nama : ");
        inName = input.nextLine();
        try {
            if(cekName("dup", inName)){
                throw new Exception("Nama Buku sudah terinput di sistem");
            }
        } catch (Exception e){
            System.out.println(e);
            inputName();
        }
    }

    public void inputFrom(){
        System.out.print("From : ");
        inFrom = input.nextLine();
        try {
            if(!cekFrom("matchRegion", inFrom)){
                throw new Exception("Wilayah tidak terdaftar di sistem");
            }
        } catch (Exception e){
            System.out.println(e);
            inputFrom();
        }
    }

    public void inputDate(){
        System.out.print("Date : ");
        inDate = input.nextInt();
    }

    public void returnMenu(){
        System.out.print("\nKembali ke menu ?");
        input.nextLine();
        String backConfirm = input.nextLine();
        mainMenu();
    }

    private void mainMenu(){
        System.out.print("\n===============================");
        System.out.println("\nWelcome to, UMM Library");
        System.out.println("CRUD with Dynamic ArraysDB");
        System.out.println("===============================");
        System.out.println("1. Book Register\n2. List Book\n3. Find Book\n4. Delete Book\n0. Exit");
        System.out.print("Pilih menu: ");
        int go = input.nextInt();
        switch (go) {
            case 1 -> registerBook();
            case 2 -> list();
            case 3 -> findBook();
            case 4 -> deleteBook();
            case 0 -> System.exit(0);
        }
    }

    public static void main(String[] args) {
        Tugas2 main = new Tugas2();
        main.book[0] = "This is Book,Malang.2022";
        main.mainMenu();
    }
}
