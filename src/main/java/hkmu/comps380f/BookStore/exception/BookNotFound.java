package hkmu.comps380f.BookStore.exception;

public class BookNotFound extends Exception {
    public BookNotFound(long id) {
        super("Book " + id + " does not exist.");
    }
}

