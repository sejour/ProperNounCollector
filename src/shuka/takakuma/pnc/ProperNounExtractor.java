package shuka.takakuma.pnc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.chasen.mecab.Node;
import org.chasen.mecab.Tagger;

public class ProperNounExtractor {

	private static final String PART_OF_SPEACH = "固有名詞";

	public static List<String> extract(String text) {
		List<String> result = new ArrayList<String>();
    	if (text == null || text.isEmpty()) return result;

    	Set<String> standardResult = standardExtract(text);

    	Tagger tagger = SharedTagger.neologd();
    	tagger.parse(text);
        Node n = tagger.parseToNode(text);

        for (;n != null; n = n.getNext()) {
            String surface = n.getSurface();
            String[] splited = n.getFeature().split(",");
            if (splited.length >= 2 && splited[1].equals(PART_OF_SPEACH) && !standardResult.contains(surface)) {
            	result.add(surface);
            }
        }

        return result;
	}

	// 標準のIPA辞書
	private static Set<String> standardExtract(String text) {
		Set<String> result = new HashSet<String>();

		Tagger tagger = SharedTagger.standard();
    	tagger.parse(text);
        Node n = tagger.parseToNode(text);

        for (;n != null; n = n.getNext()) {
            String surface = n.getSurface();
            String[] splited = n.getFeature().split(",");
            if (splited.length >= 2 && splited[1].equals(PART_OF_SPEACH)) {
            	result.add(surface);
            }
        }

        return result;
	}

}
