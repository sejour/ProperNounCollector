package shuka.takakuma.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {

	public interface BufferedReaderRecipient {

		public void receive(int statusCode, BufferedReader responseStream) throws IOException;

	}

	public static void get(String urlString, BufferedReaderRecipient recipient) {
		try {
            URL url = new URL(urlString);
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int statusCode = connection.getResponseCode();

                try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
                		BufferedReader reader = new BufferedReader(isr)) {
                	recipient.receive(statusCode, reader);
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static String getString(String urlString) {
		StringBuilder builder = new StringBuilder();

		try {
            URL url = new URL(urlString);
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.getResponseCode();

                try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
                		BufferedReader reader = new BufferedReader(isr)) {
                	String line;
                	while ((line = reader.readLine()) != null) {
                		builder.append(line);
                	}
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

		return builder.toString();
	}

}
