package org.codekatha.trigram.util;

import org.codekatha.trigram.exception.BookReadException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class BookReader{

    private BlockingQueue<String> lineQueue;
    private File book;

    public BookReader(BlockingQueue<String> queue, String filePath) throws BookReadException {
        this.lineQueue = queue;
        book = new File(filePath);

        if (lineQueue == null) {
            System.out.println("Book initialization failed");
            throw new BookReadException(
                    "Book Read initialization failed! Shared object null");
        }

        if (book.exists() == false) {
            throw new BookReadException(
                    "Bookreader intialization failed! Book does not exists");
        }
    }

    public Integer read() throws BookReadException {
        Integer lineCount = 0;
        BufferedReader bookReader = null;
        String line;
        try {
            bookReader = new BufferedReader(new FileReader(book));
            while ((line = bookReader.readLine()) != null) {
                lineCount += 1;
                lineQueue.put(line);
            }
        } catch (FileNotFoundException e) {
            throw new BookReadException("Book does not exist: "
                    + book.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new BookReadException("Error while reading book : "
                    + book.getAbsolutePath(), e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (bookReader != null) {
                try {
                    bookReader.close();
                } catch (IOException e) {
                    System.out.println("IOException {}" + e.getMessage());
                }
            }
        }
        return lineCount;
    }

}
