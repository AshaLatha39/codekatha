package org.codekatha.trigram.exception;

public class TrigramGeneratorException extends TrigramException {

    private static final long serialVersionUID = 1L;

    public TrigramGeneratorException(String message) {
        super(message);
    }

    public TrigramGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
