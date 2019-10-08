package rs.com.test_utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class StringUtil {

    private static final String NO_MATCHING_STRING = "NOMATCHINGSTRING";

    public static String getLettersFromString(String text){
        return text.replaceAll("[^a-zA-Z]", "");
    }

    public static int getNumbersFromString(String text) {return Integer.parseInt(String.valueOf(text.replaceAll("\\D+", "")));}

    public static String getDoubleFromString(String text){
        return (text.
                replaceAll("[\\s+a-zA-Z \\d\\D:]",""));
    }

    public static double getPriceFromString(String text){
        return parseDouble(text.split("£")[1]);
    }

    public static String getStringOutsideBrackets(String text) {
        return text.replaceAll("\\(.*?\\)", "");
    }

    public static String getStringInsideBrackets(String text) {
        return text.replaceAll("\\((.*?)\\)", "");
    }

    public static String getSubStringByRegex(String text, String regex) {
        return getSubStringByRegex(text, regex, 0, NO_MATCHING_STRING);
    }

    public static String getSubStringByRegex(String text, String regex, int index, String message) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        return m.find() ? m.group(index) : message;
    }
}


