package com.github.rove24.bili;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/* loaded from: classes.dex */
public class TraditionalHookEntry implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (BiliMonetHelper.TARGET_PACKAGE.equals(loadPackageParam.packageName)) {
            XposedBridge.log("TraditionalHookEntry active for " + loadPackageParam.packageName);
            initHooks(loadPackageParam.classLoader);
        }
    }

    private void initHooks(ClassLoader classLoader) {
        hookThemeUtils(classLoader);
        hookResourcesImpl();
        hookResources();
        hookLayoutInflater();
    }

    private void hookThemeUtils(ClassLoader classLoader) {
        try {
            Class findClass = XposedHelpers.findClass("com.bilibili.magicasakura.utils.ThemeUtils", classLoader);
            XposedHelpers.findAndHookMethod(findClass, "c", new Object[]{Integer.TYPE, Context.class, Integer.TYPE, new XC_MethodHook() { // from class: com.github.rove24.bili.TraditionalHookEntry.1
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    String sysNameFor;
                    int systemColor;
                    if (BiliMonetHelper.isResolving()) {
                        return;
                    }
                    try {
                        int intValue = ((Integer) methodHookParam.args[0]).intValue();
                        Context context = (Context) methodHookParam.args[1];
                        if (intValue == 0 || context == null) {
                            return;
                        }
                        Resources resources = context.getResources();
                        if (!BiliMonetHelper.TARGET_PACKAGE.equals(resources.getResourcePackageName(intValue)) || (sysNameFor = BiliMonetHelper.sysNameFor(resources.getResourceEntryName(intValue), BiliMonetHelper.isNight(resources))) == null || (systemColor = BiliMonetHelper.getSystemColor(resources, sysNameFor, context.getTheme())) == 0) {
                            return;
                        }
                        methodHookParam.setResult(Integer.valueOf(systemColor));
                    } catch (Throwable unused) {
                    }
                }
            }});
            XposedHelpers.findAndHookMethod(findClass, "b", new Object[]{Integer.TYPE, Context.class, new XC_MethodHook() { // from class: com.github.rove24.bili.TraditionalHookEntry.2
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (BiliMonetHelper.isResolving()) {
                        return;
                    }
                    try {
                        int intValue = ((Integer) methodHookParam.args[0]).intValue();
                        Context context = (Context) methodHookParam.args[1];
                        if (context != null) {
                            boolean isNight = BiliMonetHelper.isNight(context);
                            if (BiliMonetHelper.isPinkInt(intValue)) {
                                int systemColor = BiliMonetHelper.getSystemColor(context, isNight ? "system_accent1_400" : "system_neutral1_200");
                                if (systemColor != 0) {
                                    methodHookParam.setResult(Integer.valueOf(systemColor));
                                }
                            } else if (BiliMonetHelper.isLinkInt(intValue)) {
                                int systemColor2 = BiliMonetHelper.getSystemColor(context, isNight ? "system_accent3_300" : "system_accent3_600");
                                if (systemColor2 != 0) {
                                    methodHookParam.setResult(Integer.valueOf(systemColor2));
                                }
                            }
                        }
                    } catch (Throwable unused) {
                    }
                }
            }});
        } catch (Throwable th) {
            XposedBridge.log("Failed to hook ThemeUtils in TraditionalHookEntry: " + th.getMessage());
        }
    }

    private void hookResourcesImpl() {
        try {
            XposedHelpers.findAndHookMethod(XposedHelpers.findClass("android.content.res.ResourcesImpl", (ClassLoader) null), "loadColorStateList", new Object[]{Resources.class, TypedValue.class, Integer.TYPE, Resources.Theme.class, new XC_MethodHook() { // from class: com.github.rove24.bili.TraditionalHookEntry.3
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    String sysNameFor;
                    int systemColor;
                    if (BiliMonetHelper.isResolving()) {
                        return;
                    }
                    try {
                        Resources resources = (Resources) methodHookParam.args[0];
                        int intValue = ((Integer) methodHookParam.args[2]).intValue();
                        Resources.Theme theme = (Resources.Theme) methodHookParam.args[3];
                        if (intValue == 0 || resources == null || !BiliMonetHelper.TARGET_PACKAGE.equals(resources.getResourcePackageName(intValue)) || (sysNameFor = BiliMonetHelper.sysNameFor(resources.getResourceEntryName(intValue), BiliMonetHelper.isNight(resources))) == null || (systemColor = BiliMonetHelper.getSystemColor(resources, sysNameFor, theme)) == 0) {
                            return;
                        }
                        methodHookParam.setResult(ColorStateList.valueOf(systemColor));
                    } catch (Throwable unused) {
                    }
                }
            }});
        } catch (Throwable unused) {
        }
    }

    private void hookResources() {
        try {
            XposedHelpers.findAndHookMethod(Resources.class, "getColor", new Object[]{Integer.TYPE, Resources.Theme.class, new XC_MethodHook() { // from class: com.github.rove24.bili.TraditionalHookEntry.4
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    String sysNameFor;
                    int systemColor;
                    if (BiliMonetHelper.isResolving()) {
                        return;
                    }
                    try {
                        Resources resources = (Resources) methodHookParam.thisObject;
                        int intValue = ((Integer) methodHookParam.args[0]).intValue();
                        Resources.Theme theme = (Resources.Theme) methodHookParam.args[1];
                        if (intValue == 0 || resources == null || !BiliMonetHelper.TARGET_PACKAGE.equals(resources.getResourcePackageName(intValue)) || (sysNameFor = BiliMonetHelper.sysNameFor(resources.getResourceEntryName(intValue), BiliMonetHelper.isNight(resources))) == null || (systemColor = BiliMonetHelper.getSystemColor(resources, sysNameFor, theme)) == 0) {
                            return;
                        }
                        methodHookParam.setResult(Integer.valueOf(systemColor));
                    } catch (Throwable unused) {
                    }
                }
            }});
        } catch (Throwable unused) {
        }
        try {
            XposedHelpers.findAndHookMethod(Resources.class, "getColorStateList", new Object[]{Integer.TYPE, Resources.Theme.class, new XC_MethodHook() { // from class: com.github.rove24.bili.TraditionalHookEntry.5
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    String sysNameFor;
                    int systemColor;
                    if (BiliMonetHelper.isResolving()) {
                        return;
                    }
                    try {
                        Resources resources = (Resources) methodHookParam.thisObject;
                        int intValue = ((Integer) methodHookParam.args[0]).intValue();
                        Resources.Theme theme = (Resources.Theme) methodHookParam.args[1];
                        if (intValue == 0 || resources == null || !BiliMonetHelper.TARGET_PACKAGE.equals(resources.getResourcePackageName(intValue)) || (sysNameFor = BiliMonetHelper.sysNameFor(resources.getResourceEntryName(intValue), BiliMonetHelper.isNight(resources))) == null || (systemColor = BiliMonetHelper.getSystemColor(resources, sysNameFor, theme)) == 0) {
                            return;
                        }
                        methodHookParam.setResult(ColorStateList.valueOf(systemColor));
                    } catch (Throwable unused2) {
                    }
                }
            }});
        } catch (Throwable unused2) {
        }
    }

    private void hookLayoutInflater() {
        try {
            XposedHelpers.findAndHookMethod(LayoutInflater.class, "inflate", new Object[]{Integer.TYPE, ViewGroup.class, Boolean.TYPE, new XC_MethodHook() { // from class: com.github.rove24.bili.TraditionalHookEntry.6
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    Object result = methodHookParam.getResult();
                    if (result instanceof View) {
                        View view = (View) result;
                        Context context = view.getContext();
                        int intValue = ((Integer) methodHookParam.args[0]).intValue();
                        if (intValue == 0 || context == null) {
                            return;
                        }
                        try {
                            String resourceEntryName = context.getResources().getResourceEntryName(intValue);
                            if ("cmt3_main_list_layer".equals(resourceEntryName) || "theseus_detail_comment_fragment".equals(resourceEntryName)) {
                                int systemColor = BiliMonetHelper.getSystemColor(context, BiliMonetHelper.isNight(context) ? "system_accent2_800" : "system_neutral2_50");
                                if (systemColor != 0) {
                                    view.setBackgroundColor(systemColor);
                                }
                            }
                        } catch (Throwable unused) {
                        }
                    }
                }
            }});
        } catch (Throwable unused) {
        }
    }
}
