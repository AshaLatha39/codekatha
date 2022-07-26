package org.codekatha.trigram.util;

import org.codekatha.trigram.exception.TrigramGeneratorException;
import org.codekatha.trigram.model.TrigramsMap;

import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrigramsGenerator {

	private BlockingQueue<String> lineQueue;
	private TrigramsMap trigramsMap;

	public TrigramsGenerator(BlockingQueue<String> queue)
			throws TrigramGeneratorException {
		lineQueue = queue;
		trigramsMap = new TrigramsMap();

		if (lineQueue == null) {
			throw new TrigramGeneratorException(
					"TrigramsGenerator initialization failed! Line queue should not be null.");
		}

	}

	public TrigramsMap generate() throws TrigramGeneratorException {
		try {
			Pattern pattern = Pattern
					.compile("([^$]|[^\\s]*)\\s+(([^\\s]*)\\s+([^\\s]*)(\\s.*)?)");
			String line = "";
			String newLine = "";
			while (!lineQueue.isEmpty()) {
				newLine = lineQueue.take();
				if (line.endsWith(" ") && !newLine.isEmpty()) {
					line = line + newLine;
				} else if (!newLine.isEmpty()) {
					line = line + " " + newLine;
				}

				line = line.replaceAll("[\\.,\\!\":]", "");

				Matcher matcher = pattern.matcher(line);
				while (line != null && matcher.matches()) {
					if (!matcher.group(1).isEmpty()) {
						trigramsMap.put(
								matcher.group(1) + " " + matcher.group(3),
								matcher.group(4));
					}
					line = matcher.group(2);
					matcher = pattern.matcher(line);
				}
			}
			if (trigramsMap.size() == 0) {
				throw new TrigramGeneratorException(
						"Trigrams generation failed! TrigramsMap size should not be zero.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return trigramsMap;
	}

}
