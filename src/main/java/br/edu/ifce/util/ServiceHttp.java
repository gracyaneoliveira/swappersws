package br.edu.ifce.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.ifce.model.Book;

public class ServiceHttp {

	private static final String URL = "https://www.googleapis.com/books/v1/volumes/";

	public static Book connectionHttp(String idBook) throws IOException {
		Book book;
		URL url = new URL(URL + idBook);
		URLConnection conn = url.openConnection();
		conn.setDoInput(true);
		InputStream in = conn.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), Charset.forName("UTF8")));
		String inputLine;
		StringBuffer responseJson = new StringBuffer();

		while ((inputLine = br.readLine()) != null) {
			responseJson.append(inputLine);
		}
		br.close();

		book = parseJsonToBook(responseJson.toString());
		in.close();
		return book;
	}

	private static Book parseJsonToBook(String jsonBooks) {
		JSONObject json = null;
		Book book = null;
		String title = "";
		String subtitle = "";
		String desc = "";
		String publisher = "";
		String auth = "desconhecido";
		String photoLink = "";
		double rating = 0.0;
		int page = 0;

		try {
			json = new JSONObject(jsonBooks);

			book = new Book();
			JSONObject volumeInfo = json.getJSONObject("volumeInfo");

			book.setId(json.getString("id"));

			if (volumeInfo.has("title")) {
				title = volumeInfo.getString("title");
				book.setTitle(title);
			} else {
				book.setTitle(title);
			}

			if (volumeInfo.has("subtitle")) {
				subtitle = volumeInfo.getString("subtitle");
				book.setTitle(title + " - " + subtitle);
			}

			if (volumeInfo.has("publisher")) {
				publisher = volumeInfo.getString("publisher");
				book.setPublisher(publisher);
			} else {
				book.setPublisher(publisher);
			}

			if (volumeInfo.has("description")) {
				desc = volumeInfo.getString("description");
				book.setSynopsis(desc.replace("<p>", "").replace("</p>", ""));
			} else {
				book.setSynopsis(desc);
			}

			if (volumeInfo.has("averageRating")) {
				rating = volumeInfo.getDouble("averageRating");
				book.setEvaluationAverage((float) rating);
			} else {
				book.setEvaluationAverage((float) rating);
			}

			if (volumeInfo.has("authors")) {
				JSONArray authors = volumeInfo.getJSONArray("authors");
				auth = "";
				if (authors.length() > 1) {
					for (int k = 0; k < authors.length(); k++) {
						auth = auth + authors.getString(k) + " e ";
					}
					book.setAuthor(auth.substring(0, auth.length() - 2));
				} else {
					book.setAuthor(authors.getString(0));
				}
			} else {
				book.setAuthor(auth);
			}

			if (volumeInfo.has("imageLinks")) {
				JSONObject imageLink = volumeInfo.getJSONObject("imageLinks");
				book.setPhoto(imageLink.getString("thumbnail"));
			} else {
				book.setPhoto(photoLink);
			}

			if (volumeInfo.has("pageCount")) {
				page = volumeInfo.getInt("pageCount");
				book.setNumberPage(page);
			} else {
				book.setNumberPage(page);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return book;
	}
}
