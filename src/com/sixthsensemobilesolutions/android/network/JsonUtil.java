package com.sixthsensemobilesolutions.android.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	private static Gson gson;

	static {
		setupGson();
	}

	public static <T> T parseAsJson(boolean isContentEncodingGzip, InputStream inputStream, Class<T> clazz) throws GsonParsingException {
		InputStreamReader inputStreamReader = null;
		T response;
		try {
			inputStreamReader = getTheInputStream(isContentEncodingGzip, inputStream, inputStreamReader);
			response = gson.fromJson(inputStreamReader, clazz);
		} catch (Exception ex) {
			throw new GsonParsingException(ex.getMessage());
		}
		return response;
	}

	private static InputStreamReader getTheInputStream(boolean isContentEncodingGzip, InputStream inputStream, InputStreamReader inputStreamReader) throws UnsupportedEncodingException, IOException {
		if (isContentEncodingGzip) {
			inputStreamReader = new InputStreamReader(new GZIPInputStream(inputStream), "UTF-8");
		} else {
			inputStreamReader = new InputStreamReader(inputStream);
		}
		return inputStreamReader;
	}

	public static String fromStream(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}
		return out.toString();
	}

	private static void setupGson() {
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();
	}

	public static String objectToJson(Object object) {
		return gson.toJson(object);
	}

	public static <T> T stringToObject(String string, Class<T> clazz) {
		return gson.fromJson(string, clazz);
	}

	public static String objectToString(Object object) {
		return gson.toJson(object);
	}

}
