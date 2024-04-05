package hkmu.comps380f.BookStore.exception;

import java.util.UUID;

public class CommentNotFound extends Exception {
    public CommentNotFound(UUID id) {
        super("Comment " + id + " does not exist.");
    }

}
