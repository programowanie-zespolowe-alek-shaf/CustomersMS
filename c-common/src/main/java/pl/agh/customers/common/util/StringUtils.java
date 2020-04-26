package pl.agh.customers.common.util;

public class StringUtils {

    public static String addSlashes(String s) {
        s = s.replaceAll("\\\\", "\\\\\\\\");
        s = s.replaceAll("\\n", "\\\\n");
        s = s.replaceAll("\\r", "\\\\r");
        s = s.replaceAll("\\00", "\\\\0");
        s = s.replaceAll("'", "\\\\'");
        return s;
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
