package ca.jrvs.apps.trading.util;

public class StringUtil {
    public static boolean isEmpty(String s) {
        if (s.length() == 0 || s == null) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (!isEmpty(str)) {
                return false;
            }
        }
        return true;
    }
}
