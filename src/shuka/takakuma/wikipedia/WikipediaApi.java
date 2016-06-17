package shuka.takakuma.wikipedia;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import shuka.takakuma.net.HttpClient;
import shuka.takakuma.wikipedia.model.Category;
import shuka.takakuma.wikipedia.model.Page;

public class WikipediaApi {

	private static String baseUrl = "https://ja.wikipedia.org/w/api.php";

	public static List<Category> getCategoriesFromTitle(String title) throws JsonProcessingException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "query");
		params.put("prop", "categories");
		params.put("titles", URLEncoder.encode(title, "utf-8"));

		ObjectMapper mapper = new ObjectMapper();
		List<Category> results = new ArrayList<Category>();

		String text = get(params);
		if (text != null) {
			JsonNode pages = mapper.readTree(text).get("query").get("pages");
			String field = pages.fieldNames().next();
			if (field != null) {
				Page page = mapper.readValue(pages.get(field).toString(), Page.class);
				List<Category> categories = page.categories;
				if (categories != null) results.addAll(categories);
			}
		}

		return results;
	}

	public static String get(Map<String, String> params) {
		String query = generateQuery(params);
		String url = baseUrl + query;
		System.out.println("[URL] " + url);

		return HttpClient.getString(url);
	}

	private static String generateQuery(Map<String, String> params) {
		StringBuilder builder = new StringBuilder("?format=json");
		if (params == null) return builder.toString();

		for (String key : params.keySet()) {
	        builder.append(String.format("&%s=%s", key, params.get(key)));
	    }

		return builder.toString();
	}

}
