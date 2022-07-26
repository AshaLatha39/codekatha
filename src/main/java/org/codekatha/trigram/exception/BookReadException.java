package org.codekatha.trigram.exception;

public class BookReadException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookReadException(String message) {
        super(message);
    }

    public BookReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
