package org.codekatha.trigram.util;

import org.codekatha.trigram.exception.BookWriteException;
import org.codekatha.trigram.model.TrigramsMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class BookWriter{

	private File dstBook;
	private TrigramsMap trigramsMap;
	private BlockingQueue<String> lineQueue;

	public BookWriter(String dstFilePath, TrigramsMap trigramsMap,
			BlockingQueue<String> lineQueue)
			throws BookWriteException {
		this.dstBook = new File(dstFilePath);
		this.trigramsMap = trigramsMap;
		this.lineQueue = lineQueue;

		if (!dstBook.exists()) {
			try {
				dstBook.createNewFile();
			} catch (IOException e) {
				throw new BookWriteException("BookWriter initialization failed! Unable to create book at "
						+ dstBook.getAbsolutePath(), e);
			}
		}

		if (trigramsMap == null) {
			throw new BookWriteException("BookWriter initialization failed! TrigramMap should not be null");
		}

		if (lineQueue == null) {
			throw new BookWriteException(
					"BookWriter initialization failed! Shared object line queue should not be null");
		}

	}

	private String buildNextKey(String searchKey, String nextWord) {
		String[] keyParts = searchKey.split(" ");
		if (keyParts.length != 2) {
			throw new RuntimeException("Trigram key is not of 2 words!");
		}
		searchKey = keyParts[1];
		searchKey += " " + nextWord;
		return searchKey;
	}

	public Integer write() throws BookWriteException {
		Integer lineCount = 0;
		BufferedWriter bookWriter = null;
		String line = "";
		StringBuilder transformedLine = new StringBuilder();
		String searchKey = "";

		try {
			bookWriter = new BufferedWriter(new FileWriter(dstBook));
			searchKey = trigramsMap.getRandomKey();
			if (searchKey == null) {
				throw new BookWriteException(
						"Unable to get random key from trigram!");
			}
			transformedLine.append(searchKey);
			while (!lineQueue.isEmpty()) {
				lineCount += 1;
				line = lineQueue.take();
				String[] words = line.split("\\s");
				for (String word : words) {
					char specialChar = '\0';
					if (word.matches(".*[\\.,\\!\":]$")) {
						specialChar = word.charAt(word.length() - 1);
					}
					if (word.isEmpty()) {
						transformedLine.append(word);
						continue;
					}

					if (!transformedLine.toString().endsWith(" ")) {
						transformedLine.append(" ");
					}

					String transformedWord = trigramsMap.get(searchKey);
					if (transformedWord == null) {
						searchKey = trigramsMap.getRandomKey();
						if (searchKey == null) {
							throw new BookWriteException(
									"Unable to get random key from trigram!");
						}
						transformedWord = trigramsMap.get(searchKey);
					}

					searchKey = buildNextKey(searchKey, transformedWord);

					transformedLine.append(transformedWord);
					if (specialChar != '\0') {
						transformedLine.append(specialChar);
					}
				}
				bookWriter.append(transformedLine);
				bookWriter.newLine();
				transformedLine = new StringBuilder();
			}
		} catch (IOException e) {
			throw new BookWriteException("Book writing failed!", e);
		} catch (InterruptedException e) {
			System.out.println("Exception {}" + e.getMessage());
		} finally {
			if (bookWriter != null) {
				try {
					bookWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lineCount;
	}
}
