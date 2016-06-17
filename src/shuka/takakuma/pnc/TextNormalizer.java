package shuka.takakuma.pnc;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextNormalizer {

	private enum Status {
		TEXT, SCRIPT
	}

	private Status status = Status.TEXT;

	public TextNormalizer() {
	}

	public String normalizeWebText(String text) {
		return removeTag (
				baseNormalize (
				this.removeScript (
						text)));
	}

	public static String baseNormalize(String text) {
		return Normalizer.normalize(text, Normalizer.Form.NFKC)
				.replaceAll("[˗֊‐‑‒–⁃⁻₋−]+", "-")
				.replaceAll("[﹣－ｰ—―─━ー]+", "ー")
				.replaceAll("[~∼∾〜〰～]", "")
				.replaceAll("[ 　]+",  " ")
				.trim();
	}

	public static String removeTag(String text) {
		StringBuilder builder = new StringBuilder();
		int length = text.length();
		for (int i = 0; i < length; ++i) {
			char c = text.charAt(i);
			if (c == '<') {
				for (++i; i < length; ++i) {
					if (text.charAt(i) == '>') break;
				}
				builder.append(' ');
			}
			else {
				builder.append(c);
			}
		}

		return builder.toString().trim();
	}

	public String removeScript(String text) {
		// 1行で完結するscriptを削除
		text = text.replaceAll("<script.*>.*</script>", "");

		// 複数行のscriptの終端を見つけ次第削除
		Pattern pattern = Pattern.compile(".*</script>");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			this.status = Status.TEXT;
			text = text.substring(matcher.end());
		}
		else if (this.status == Status.SCRIPT) {
			text = "";
		}

		// 複数行のscriptの先頭行を見つけ次第削除
		pattern = Pattern.compile("<script.*>.*");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			this.status = Status.SCRIPT;
			text = text.substring(0, matcher.start());
		}

		return text;
	}

}
