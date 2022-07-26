package org.codekatha.trigram.exception;

public class TrigramException extends Exception {

    private static final long serialVersionUID = 1L;

    public TrigramException(String message) {
        super(message);
    }

    public TrigramException(String message, Throwable cause) {
        super(message, cause);
    }
}
