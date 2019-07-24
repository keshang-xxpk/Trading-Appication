package ca.jrvs.apps.trading.util;

public class StringUtil {
    public boolean isEmpty(String s) {
        if (s.length() == 0 || s == null) {
            return false;
        }
        return true;
    }
}
