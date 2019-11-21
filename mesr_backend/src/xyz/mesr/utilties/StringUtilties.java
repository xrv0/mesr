package xyz.mesr.utilties;

public class StringUtilties {
    public static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }
}
