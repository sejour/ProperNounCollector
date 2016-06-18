package shuka.takakuma.pnc;

import java.util.List;

import shuka.takakuma.net.HttpClient;
import shuka.takakuma.wikipedia.WikipediaApi;
import shuka.takakuma.wikipedia.model.Category;

public class ProperNounCollector {

	public static void main(String[] args) {
		ProperNounCollector pnc = new ProperNounCollector();
		pnc.collect("https://ja.wikipedia.org/wiki/気象");
		//https://ja.wikipedia.org/wiki/曲名一覧
		//https://ja.wikipedia.org/wiki/Category:音楽のジャンル
		//https://ja.wikipedia.org/wiki/新語・流行語大賞
		//https://ja.wikipedia.org/wiki/日本の地方公共団体一覧
		//https://ja.wikipedia.org/wiki/日本の企業一覧_(サービス)
		//https://ja.wikipedia.org/wiki/日本の企業一覧_(情報・通信)
		//https://ja.wikipedia.org/wiki/日本のテレビ番組一覧
		//https://ja.wikipedia.org/wiki/学問の一覧
		//https://ja.wikipedia.org/wiki/日本学術会議協力学術研究団体
		//https://ja.wikipedia.org/wiki/Category:日本のギネス世界記録
		//https://ja.wikipedia.org/wiki/コンピュータ用語一覧
		//https://ja.wikipedia.org/wiki/日本の記念日一覧
		//https://ja.wikipedia.org/wiki/超高層ビルの一覧
		//https://ja.wikipedia.org/wiki/ファッションブランド一覧
		//https://ja.wikipedia.org/wiki/気象
	}

	public ProperNounCollector() {
		try {
			System.loadLibrary("MeCab");
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
	}

	public void collect(String url) {
		HttpClient.get(url, (statusCode, reader) -> {
			TextNormalizer normalizer = new TextNormalizer();
			Writer writer = new Writer();
			String line;
			int count = 0;
			System.out.println("[start]");
            while ((line = reader.readLine()) != null) {
            	List<String> words = ProperNounExtractor.extract(normalizer.normalizeWebText(line));
            	for (String word : words) {
            		++count;
            		System.out.printf("[%d] %s\n", count, word);
            		List<Category> categories = WikipediaApi.getCategoriesFromTitle(word);
            		writer.write(word, categories);
            	}
            }
            writer.close();
            System.out.printf("[end] (%d)\n", count);
		});
	}

}
