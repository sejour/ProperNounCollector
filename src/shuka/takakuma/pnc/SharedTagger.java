package shuka.takakuma.pnc;

import org.chasen.mecab.Tagger;

public class SharedTagger {

	// 標準のIPA辞書
	private static Tagger standardInstance;
	public static Tagger standard() {
		if (standardInstance == null) {
			standardInstance = new Tagger("");
		}

		return standardInstance;
	}

	// Neologd
	private static Tagger neologdInstance;
	public static Tagger neologd() {
		if (neologdInstance == null) {
			neologdInstance = new Tagger("-Ochasen -d /usr/local/lib/mecab/dic/mecab-ipadic-neologd/");
		}

		return neologdInstance;
	}

}
