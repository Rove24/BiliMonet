package com.github.rove24.bili;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class BiliMonetHelper {
    private static final Map<String, Integer> COLOR_INT_CACHE;
    private static final Map<String, String> DAY_MAP;
    private static final Map<String, String> NIGHT_MAP;
    private static final Map<String, Integer> SYSTEM_COLOR_IDS;
    public static final String TAG = "BiliMonet";
    public static final String TARGET_PACKAGE = "tv.danmaku.bili";
    private static final ThreadLocal<Boolean> sResolving;

    public static boolean isDefaultCommentBg(int i) {
        int i2 = i & 16777215;
        return i2 == 1513498 || i2 == 1579292 || i2 == 1184274 || i2 == 16777215 || i2 == 16185336 || i2 == 2237221;
    }

    public static boolean isLinkInt(int i) {
        int i2 = i & 16777215;
        return i2 == 1280447 || i2 == 41430 || i2 == 2338277 || i2 == 44780;
    }

    public static boolean isPinkInt(int i) {
        int i2 = i & 16777215;
        return i2 == 16478873 || i2 == 16737945 || i2 == 16413336 || i2 == 15883662 || i2 == 15427976;
    }

    static {
        HashMap hashMap = new HashMap(800);
        DAY_MAP = hashMap;
        HashMap hashMap2 = new HashMap(250);
        NIGHT_MAP = hashMap2;
        SYSTEM_COLOR_IDS = new ConcurrentHashMap();
        COLOR_INT_CACHE = new ConcurrentHashMap();
        sResolving = new ThreadLocal<>();
        BiliFullMap.fillDay(hashMap);
        BiliFullMap.fillNight(hashMap2);
    }

    private BiliMonetHelper() {
    }

    public static boolean isNight(Resources resources) {
        if (resources == null) {
            return false;
        }
        try {
            Configuration configuration = resources.getConfiguration();
            if (configuration != null) {
                return (configuration.uiMode & 48) == 32;
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isNight(Context context) {
        if (context == null) {
            return false;
        }
        return isNight(context.getResources());
    }

    public static boolean isResolving() {
        Boolean bool = sResolving.get();
        return bool != null && bool.booleanValue();
    }

    public static String sysNameFor(String str, boolean z) {
        String str2;
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (z && (str2 = NIGHT_MAP.get(str)) != null) {
            return str2;
        }
        String str3 = DAY_MAP.get(str);
        if (str3 != null) {
            return str3;
        }
        String lowerCase = str.toLowerCase();
        if (lowerCase.contains("transparent")) {
            return null;
        }
        if (lowerCase.contains("cmt") || lowerCase.contains("comment")) {
            if (lowerCase.contains("link") || lowerCase.contains("high_light") || lowerCase.contains("anchor")) {
                return z ? "system_accent3_300" : "system_accent3_600";
            }
            if (lowerCase.contains("pink") || lowerCase.contains("brand") || lowerCase.contains("pi5") || lowerCase.contains("like")) {
                return z ? "system_accent1_400" : "system_neutral1_200";
            }
            if (lowerCase.contains("input") && (lowerCase.contains("bg") || lowerCase.contains("bar"))) {
                return z ? "system_accent2_900" : "system_accent1_100";
            }
            if (lowerCase.contains("bg") || lowerCase.contains("background") || lowerCase.contains("window") || lowerCase.contains("layer")) {
                return z ? "system_accent2_800" : "system_neutral2_50";
            }
        }
        if (lowerCase.startsWith("bg") && (lowerCase.contains("dark") || lowerCase.contains("black"))) {
            return z ? "system_accent2_800" : "system_neutral2_50";
        }
        return null;
    }

    public static int getSystemColor(Resources resources, String str, Resources.Theme theme) {
        int i = 0;
        if (str == null || resources == null) {
            return 0;
        }
        ThreadLocal<Boolean> threadLocal = sResolving;
        Boolean bool = threadLocal.get();
        if (bool != null && bool.booleanValue()) {
            return 0;
        }
        threadLocal.set(Boolean.TRUE);
        try {
            String str2 = str + "#" + isNight(resources);
            Integer num = COLOR_INT_CACHE.get(str2);
            if (num != null) {
                int intValue = num.intValue();
                threadLocal.set(Boolean.FALSE);
                return intValue;
            }
            int systemColorResId = getSystemColorResId(str);
            if (systemColorResId != 0) {
                try {
                    try {
                        i = resources.getColor(systemColorResId, theme);
                    } catch (Throwable unused) {
                    }
                } catch (Throwable unused2) {
                    i = Resources.getSystem().getColor(systemColorResId, null);
                }
            }
            if (i != 0) {
                COLOR_INT_CACHE.put(str2, Integer.valueOf(i));
            }
            return i;
        } finally {
            sResolving.set(Boolean.FALSE);
        }
    }

    public static int getSystemColor(Context context, String str) {
        if (context == null || str == null) {
            return 0;
        }
        return getSystemColor(context.getResources(), str, context.getTheme());
    }

    public static int getSystemColorResId(String str) {
        int i;
        Integer num = SYSTEM_COLOR_IDS.get(str);
        if (num != null) {
            return num.intValue();
        }
        try {
            i = R.color.class.getField(str).getInt(null);
        } catch (Throwable unused) {
            i = 0;
        }
        if (i == 0) {
            try {
                i = Resources.getSystem().getIdentifier(str, "color", "android");
            } catch (Throwable unused2) {
            }
        }
        if (i != 0) {
            SYSTEM_COLOR_IDS.put(str, Integer.valueOf(i));
        }
        return i;
    }

    public static void clearCache() {
        COLOR_INT_CACHE.clear();
    }
}
