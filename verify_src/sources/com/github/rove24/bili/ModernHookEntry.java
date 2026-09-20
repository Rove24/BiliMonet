package com.github.rove24.bili;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface;

/* loaded from: classes.dex */
public class ModernHookEntry extends XposedModule {
    static /* synthetic */ void lambda$new$0() {
    }

    public ModernHookEntry() {
    }

    public ModernHookEntry(XposedInterface xposedInterface, XposedModuleInterface.ModuleLoadedParam moduleLoadedParam) {
        attachFramework(xposedInterface, new Runnable() { // from class: com.github.rove24.bili.ModernHookEntry$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ModernHookEntry.lambda$new$0();
            }
        });
    }

    public void onPackageReady(XposedModuleInterface.PackageReadyParam packageReadyParam) {
        if (BiliMonetHelper.TARGET_PACKAGE.equals(packageReadyParam.getPackageName())) {
            log(3, BiliMonetHelper.TAG, "ModernHookEntry (API 102) active for " + packageReadyParam.getPackageName());
            initHooks(packageReadyParam.getClassLoader());
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
            Class<?> loadClass = classLoader.loadClass("com.bilibili.magicasakura.utils.ThemeUtils");
            hook(loadClass.getDeclaredMethod("c", Integer.TYPE, Context.class, Integer.TYPE)).intercept(new XposedInterface.Hooker() { // from class: com.github.rove24.bili.ModernHookEntry$$ExternalSyntheticLambda4
                public final Object intercept(XposedInterface.Chain chain) {
                    return ModernHookEntry.lambda$hookThemeUtils$1(chain);
                }
            });
            hook(loadClass.getDeclaredMethod("b", Integer.TYPE, Context.class)).intercept(new XposedInterface.Hooker() { // from class: com.github.rove24.bili.ModernHookEntry$$ExternalSyntheticLambda5
                public final Object intercept(XposedInterface.Chain chain) {
                    return ModernHookEntry.lambda$hookThemeUtils$2(chain);
                }
            });
            log(3, BiliMonetHelper.TAG, "Hooked MagicaSakura ThemeUtils successfully");
        } catch (Throwable th) {
            log(5, BiliMonetHelper.TAG, "ThemeUtils hook failed: " + th.getMessage());
        }
    }

    static /* synthetic */ Object lambda$hookThemeUtils$1(XposedInterface.Chain chain) throws Throwable {
        String sysNameFor;
        int systemColor;
        if (BiliMonetHelper.isResolving()) {
            return chain.proceed();
        }
        try {
            int intValue = ((Integer) chain.getArg(0)).intValue();
            Context context = (Context) chain.getArg(1);
            if (intValue != 0 && context != null) {
                Resources resources = context.getResources();
                if (BiliMonetHelper.TARGET_PACKAGE.equals(resources.getResourcePackageName(intValue)) && (sysNameFor = BiliMonetHelper.sysNameFor(resources.getResourceEntryName(intValue), BiliMonetHelper.isNight(resources))) != null && (systemColor = BiliMonetHelper.getSystemColor(resources, sysNameFor, context.getTheme())) != 0) {
                    return Integer.valueOf(systemColor);
                }
            }
        } catch (Throwable unused) {
        }
        return chain.proceed();
    }

    static /* synthetic */ Object lambda$hookThemeUtils$2(XposedInterface.Chain chain) throws Throwable {
        if (BiliMonetHelper.isResolving()) {
            return chain.proceed();
        }
        try {
            int intValue = ((Integer) chain.getArg(0)).intValue();
            Context context = (Context) chain.getArg(1);
            if (context != null) {
                boolean isNight = BiliMonetHelper.isNight(context);
                if (BiliMonetHelper.isPinkInt(intValue)) {
                    int systemColor = BiliMonetHelper.getSystemColor(context, isNight ? "system_accent1_400" : "system_neutral1_200");
                    if (systemColor != 0) {
                        return Integer.valueOf(systemColor);
                    }
                } else if (BiliMonetHelper.isLinkInt(intValue)) {
                    int systemColor2 = BiliMonetHelper.getSystemColor(context, isNight ? "system_accent3_300" : "system_accent3_600");
                    if (systemColor2 != 0) {
                        return Integer.valueOf(systemColor2);
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return chain.proceed();
    }

    private void hookResourcesImpl() {
        try {
            hook(Class.forName("android.content.res.ResourcesImpl").getDeclaredMethod("loadColorStateList", Resources.class, TypedValue.class, Integer.TYPE, Resources.Theme.class)).intercept(new XposedInterface.Hooker() { // from class: com.github.rove24.bili.ModernHookEntry$$ExternalSyntheticLambda6
                public final Object intercept(XposedInterface.Chain chain) {
                    return ModernHookEntry.lambda$hookResourcesImpl$3(chain);
                }
            });
        } catch (Throwable unused) {
        }
    }

    static /* synthetic */ Object lambda$hookResourcesImpl$3(XposedInterface.Chain chain) throws Throwable {
        String sysNameFor;
        int systemColor;
        if (BiliMonetHelper.isResolving()) {
            return chain.proceed();
        }
        try {
            Resources resources = (Resources) chain.getArg(0);
            int intValue = ((Integer) chain.getArg(2)).intValue();
            Resources.Theme theme = (Resources.Theme) chain.getArg(3);
            if (intValue != 0 && resources != null && BiliMonetHelper.TARGET_PACKAGE.equals(resources.getResourcePackageName(intValue)) && (sysNameFor = BiliMonetHelper.sysNameFor(resources.getResourceEntryName(intValue), BiliMonetHelper.isNight(resources))) != null && (systemColor = BiliMonetHelper.getSystemColor(resources, sysNameFor, theme)) != 0) {
                return ColorStateList.valueOf(systemColor);
            }
        } catch (Throwable unused) {
        }
        return chain.proceed();
    }

    private void hookResources() {
        try {
            hook(Resources.class.getMethod("getColor", Integer.TYPE, Resources.Theme.class)).intercept(new XposedInterface.Hooker() { // from class: com.github.rove24.bili.ModernHookEntry$$ExternalSyntheticLambda1
                public final Object intercept(XposedInterface.Chain chain) {
                    return ModernHookEntry.lambda$hookResources$4(chain);
                }
            });
        } catch (Throwable unused) {
        }
        try {
            hook(Resources.class.getMethod("getColorStateList", Integer.TYPE, Resources.Theme.class)).intercept(new XposedInterface.Hooker() { // from class: com.github.rove24.bili.ModernHookEntry$$ExternalSyntheticLambda2
                public final Object intercept(XposedInterface.Chain chain) {
                    return ModernHookEntry.lambda$hookResources$5(chain);
                }
            });
        } catch (Throwable unused2) {
        }
    }

    static /* synthetic */ Object lambda$hookResources$4(XposedInterface.Chain chain) throws Throwable {
        String sysNameFor;
        int systemColor;
        if (BiliMonetHelper.isResolving()) {
            return chain.proceed();
        }
        try {
            Resources resources = (Resources) chain.getThisObject();
            int intValue = ((Integer) chain.getArg(0)).intValue();
            Resources.Theme theme = (Resources.Theme) chain.getArg(1);
            if (intValue != 0 && resources != null && BiliMonetHelper.TARGET_PACKAGE.equals(resources.getResourcePackageName(intValue)) && (sysNameFor = BiliMonetHelper.sysNameFor(resources.getResourceEntryName(intValue), BiliMonetHelper.isNight(resources))) != null && (systemColor = BiliMonetHelper.getSystemColor(resources, sysNameFor, theme)) != 0) {
                return Integer.valueOf(systemColor);
            }
        } catch (Throwable unused) {
        }
        return chain.proceed();
    }

    static /* synthetic */ Object lambda$hookResources$5(XposedInterface.Chain chain) throws Throwable {
        String sysNameFor;
        int systemColor;
        if (BiliMonetHelper.isResolving()) {
            return chain.proceed();
        }
        try {
            Resources resources = (Resources) chain.getThisObject();
            int intValue = ((Integer) chain.getArg(0)).intValue();
            Resources.Theme theme = (Resources.Theme) chain.getArg(1);
            if (intValue != 0 && resources != null && BiliMonetHelper.TARGET_PACKAGE.equals(resources.getResourcePackageName(intValue)) && (sysNameFor = BiliMonetHelper.sysNameFor(resources.getResourceEntryName(intValue), BiliMonetHelper.isNight(resources))) != null && (systemColor = BiliMonetHelper.getSystemColor(resources, sysNameFor, theme)) != 0) {
                return ColorStateList.valueOf(systemColor);
            }
        } catch (Throwable unused) {
        }
        return chain.proceed();
    }

    private void hookLayoutInflater() {
        try {
            hook(LayoutInflater.class.getMethod("inflate", Integer.TYPE, ViewGroup.class, Boolean.TYPE)).intercept(new XposedInterface.Hooker() { // from class: com.github.rove24.bili.ModernHookEntry$$ExternalSyntheticLambda0
                public final Object intercept(XposedInterface.Chain chain) {
                    return ModernHookEntry.lambda$hookLayoutInflater$6(chain);
                }
            });
        } catch (Throwable unused) {
        }
    }

    static /* synthetic */ Object lambda$hookLayoutInflater$6(XposedInterface.Chain chain) throws Throwable {
        Object proceed = chain.proceed();
        if (proceed instanceof View) {
            View view = (View) proceed;
            Context context = view.getContext();
            int intValue = ((Integer) chain.getArg(0)).intValue();
            if (intValue != 0 && context != null) {
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
        return proceed;
    }
}
