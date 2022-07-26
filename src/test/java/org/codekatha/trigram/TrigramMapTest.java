package org.codekatha.trigram;

import org.codekatha.trigram.exception.BookReadException;
import org.codekatha.trigram.exception.BookWriteException;
import org.codekatha.trigram.model.TrigramsMap;
import org.codekatha.trigram.util.BookReader;
import org.codekatha.trigram.util.BookWriter;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

public class TrigramMapTest {

    @Test(timeout = 100)
    public void test_ReadBook() throws BookReadException {
        ArrayBlockingQueue<String> lineQueue = new ArrayBlockingQueue<>(
                1);
        String testFile = "/Users/tirveedhulaa/Downloads/Triagram/src/test/java/org/codekatha/trigram/test.txt";
        BookReader bookReader = new BookReader(lineQueue, testFile);
        Integer result = bookReader.read();
        Assert.assertEquals(1,result.intValue());
    }

    @Test
    public void test_WriteToBook() throws BookWriteException, InterruptedException {
        ArrayBlockingQueue<String> lineQueue = new ArrayBlockingQueue<>(
                1);
         lineQueue.put("I Wish");
        TrigramsMap trigrams = new TrigramsMap();
        trigrams.put("I Wish","I,I");
        String testFile = "/Users/tirveedhulaa/Downloads/Triagram/src/test/java/org/codekatha/trigram/output.txt";
        BookWriter bookWriter = new BookWriter(testFile,trigrams,lineQueue);
        Integer result = bookWriter.write();
        Assert.assertEquals(1,result.intValue());
    }
}
