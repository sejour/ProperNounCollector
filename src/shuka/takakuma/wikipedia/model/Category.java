package shuka.takakuma.wikipedia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Category {

	@JsonProperty("ns")
	public String ns;

	@JsonProperty("title")
	public String title;

	@JsonIgnore
	public String category() {
		return title.replace("Category:", "");
	}
	
	@Override
	public String toString() {
		return this.category();
	}

}
