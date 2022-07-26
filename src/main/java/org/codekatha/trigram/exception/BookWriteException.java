package org.codekatha.trigram.exception;

public class BookWriteException extends TrigramException {

	private static final long serialVersionUID = 1L;

	public BookWriteException(String message) {
		super(message);
	}

	public BookWriteException(String message, Throwable cause) {
		super(message, cause);
	}

}
