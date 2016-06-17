package shuka.takakuma.pnc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shuka.takakuma.wikipedia.model.Category;

public class Writer {

	private static final String FILE_PATH = "results/words.txt";

	private Set<String> registered = new HashSet<String>();
	private PrintWriter writer;

	public Writer() throws IOException {
		File output = new File(FILE_PATH);

		if (output.exists()) {
			// 前回までの収集結果の読み込み
			try (BufferedReader reader = new BufferedReader(new FileReader(output))) {
				String line;
				while ((line = reader.readLine()) != null) {
					registered.add(line.split("\t")[0]);
				}
			}
		}

		this.writer = new PrintWriter(new BufferedWriter(new FileWriter(output, true)));
	}

	public void write(String word, List<Category> categories) {
		// 同じ単語を重複して書き込まない
		if (this.registered.contains(word) || categories == null || categories.isEmpty()) return;
		this.writer.printf("%s\t\t\t\t%s\n", word, metaText(categories));
		this.registered.add(word);
	}

	private static String metaText(List<Category> categories) {
		StringBuilder builder = new StringBuilder();

		for (Category category: categories) {
			if (builder.length() != 0) {
				builder.append("|");
			}
			builder.append(category.category());
		}

		return builder.toString();
	}

	public void close() {
		this.writer.close();
	}

}
