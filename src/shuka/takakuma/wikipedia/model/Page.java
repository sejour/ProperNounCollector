package shuka.takakuma.wikipedia.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Page {

	@JsonProperty("pageid")
	public Integer pageId;

	@JsonProperty("ns")
	public String ns;

	@JsonProperty("title")
	public String title;

	@JsonProperty("categories")
	public List<Category> categories;

}
