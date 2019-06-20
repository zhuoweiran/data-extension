package cn.csg.jobschedule.util;

public class CommonUtil {
    public static boolean checkAddress(String s) {
        return s.matches("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))");
    }

    public static boolean checkPort(String s) {
        return s.matches("^[1-9]$|(^[1-9][0-9]$)|(^[1-9][0-9][0-9]$)|(^[1-9][0-9][0-9][0-9]$)|(^[1-6][0-5][0-5][0-3][0-5]$)");
    }

    public static String changeNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static String changeNull(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }

}
