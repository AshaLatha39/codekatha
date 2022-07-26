package org.codekatha.trigram;

import org.codekatha.trigram.exception.BookReadException;
import org.codekatha.trigram.exception.TrigramException;
import org.codekatha.trigram.model.TrigramsMap;
import org.codekatha.trigram.util.BookReader;
import org.codekatha.trigram.util.BookWriter;
import org.codekatha.trigram.util.TrigramsGenerator;

import java.util.concurrent.ArrayBlockingQueue;

public class TrigramMain {

    public TrigramsMap buildTrigramFromInput(String filePath,
                                             int readerQueueCapacity) throws TrigramException, BookReadException {
        ArrayBlockingQueue<String> lineQueue = new ArrayBlockingQueue<>(
                readerQueueCapacity);
        BookReader bookReader = new BookReader(lineQueue, filePath);
        Integer bookReaderResult = bookReader.read();
        TrigramsGenerator trigramsGenerator = new TrigramsGenerator(lineQueue);

        TrigramsMap trigramsGeneratorResult = trigramsGenerator.generate();
        System.out.println(bookReaderResult + " processed from " + filePath);
        return trigramsGeneratorResult;
    }

    public void writeBookFromTrigram(TrigramsMap trigramsMap,
                                     String srcFileName, String dstFileName, int readerQueueCapacity)
            throws TrigramException, BookReadException {
        ArrayBlockingQueue<String> lineQueue = new ArrayBlockingQueue<>(
                readerQueueCapacity);
        BookReader bookReader = new BookReader(lineQueue, srcFileName);
        BookWriter bookWriter = new BookWriter(dstFileName, trigramsMap,
                lineQueue);
        Integer bookReaderResult = bookReader.read();
        Integer bookWriterResult = bookWriter.write();
        System.out.println(bookReaderResult + " processed from " + srcFileName);
        System.out.println(bookWriterResult + " lines written to " + dstFileName);

    }

    public static void main(String[] args) {
        TrigramMain trigramMain = new TrigramMain();
        TrigramsMap trigramMap = null;
        try {
            if (args.length != 1) {
                System.out.println("Book name argument is missing!");
                return;
            }
            String srcFileName = args[0];
            System.out.println();
            String dstFileName = args[0].substring(0, args[0].indexOf('.'))
                    + "_" + System.currentTimeMillis() + ".txt";
            System.out.println("Creating trigrams from book!");
            trigramMap = trigramMain.buildTrigramFromInput(srcFileName, 100000);
            System.out.println("Re writing new book using trigrams!");
            trigramMain.writeBookFromTrigram(trigramMap, srcFileName,
                    dstFileName, 100000);

        } catch (TrigramException | BookReadException e) {
            e.printStackTrace();
        }
    }
}

