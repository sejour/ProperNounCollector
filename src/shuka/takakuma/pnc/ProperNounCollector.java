package shuka.takakuma.pnc;

import java.util.List;

import shuka.takakuma.net.HttpClient;

public class ProperNounCollector {

	public static void main(String[] args) {
		ProperNounCollector pnc = new ProperNounCollector();
		pnc.collect("https://ja.wikipedia.org/wiki/曲名一覧");
	}

	public ProperNounCollector() {
		try {
			System.loadLibrary("MeCab");
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
	}

	public void collect(String url) {
		System.out.println("[start]");
		HttpClient.get(url, (statusCode, reader) -> {
			TextNormalizer normalizer = new TextNormalizer();
			String line;
			int count = 0;
            while ((line = reader.readLine()) != null) {
            	//System.out.println(line);
            	List<String> tokens = ProperNounExtractor.extract(normalizer.normalizeWebText(line));
            	for (String token : tokens) {
            		System.out.println(token);
            		++count;
            	}
            	//System.out.println("#####################################");
            }
            System.out.printf("[end] (%d)\n", count);
		});
	}

}
