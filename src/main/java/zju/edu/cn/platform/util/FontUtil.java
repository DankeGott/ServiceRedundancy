package zju.edu.cn.platform.util;

import java.awt.*;

public class FontUtil {

    public static boolean isEmptyStr(String str) {
        if (str == null || str.length() == 0)
            return true;
        return false;
    }

    public static Font getDefaultFont() {
        return new Font(null);
    }

    public static int getStringLength(Font font, String str) {
        if (isEmptyStr(str)) {
            return 0;
        }
        if (null == font) {
            font = getDefaultFont();
        }
//        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        char[] strcha = str.toCharArray();
        return metrics.charsWidth(strcha, 0, str.length());
    }

    //得到应该换行前的最大字符串
    public static String strSplitMaxLenth(Font font, String str, int maxLength) {
        if (isEmptyStr(str) || maxLength < 1) {
            return str;
        }
        if (null == font) {
            font = getDefaultFont();
        }
//        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        String max_Str = str;

        int width = 0;
        for (int i = 0; i < str.length(); i++) {
            width += metrics.charWidth(str.charAt(i));
            if (width > maxLength) {
                //上一个长度
                max_Str = str.substring(0, i);
                break;
            }
        }
        return max_Str;
    }

    //是否是最长的字符串，如果是true，就不需要再次进行截断
    public static boolean isMaxStr(Font font, String str, int maxLength) {
        if (isEmptyStr(str) || maxLength < 1) {
            return true;
        }
        if (null == font) {
            font = getDefaultFont();
        }
//        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        int width = 0;
        for (int i = 0; i < str.length(); i++) {
            width += metrics.charWidth(str.charAt(i));
            if (width > maxLength) {
                return false;
            }
        }
        return width <= maxLength;

    }

}
