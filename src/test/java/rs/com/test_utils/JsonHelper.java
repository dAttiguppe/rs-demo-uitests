package rs.com.test_utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.io.Reader;

public class JsonHelper {
    private static final JsonParser parser = new JsonParser();

    public static String getParameterFromJson(String jsonObject, String jsonParameter) {
        JsonObject jsonObj = (JsonObject) parser.parse(jsonObject);

        if (jsonObj.get(jsonParameter) != null) {
            return jsonObj.get(jsonParameter).getAsString();
        } else {
            return "";
        }
    }

    public static <T> T fromJson(String filePath, Class<T> classOfT) {
        try (Reader reader = new InputStreamReader(JsonHelper.class.getClassLoader().getResourceAsStream(filePath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(reader, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

