public class Main {
    public static void main(String[] args) {

//        Book freshBook = new Book(101, "Mysterious Island", "Jules Verne");
//        System.out.println(freshBook);

//        Loan loan = new Loan(5 ,9, 7);

//        Loan shortTermLoan = new ShortTermLoan(9, 18, 5);
//        System.out.println(shortTermLoan);

//        Loan longTermLoan = new LongTermLoan(5, 8, 1);
//        System.out.println(longTermLoan);

        Library library = new Library();
        library.addBook(new Book(1, "1984", "George Orwell"));
        library.addBook(new Book(2, "Brave New World", "Aldous Huxley"));
        library.printBooks();
    }
}