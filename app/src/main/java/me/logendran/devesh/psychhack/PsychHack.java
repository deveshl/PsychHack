package me.logendran.devesh.psychhack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findFirstFieldByExactType;

public class PsychHack implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpParam) throws Throwable {
        if (!lpParam.packageName.equals("com.wb.goog.ellen.psych"))
            return;

        findAndHookMethod("com.wb.goog.ellen.psych.controllers.game.GamePlayAnswersListController$2",
                lpParam.classLoader, "run", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Field field = findFirstFieldByExactType(param.thisObject.getClass(), List.class);
                        ArrayList<String> list = (ArrayList<String>) field.get(param.thisObject);
                        list.remove("correct");
                        list.add(0, "correct");
                        Collections.reverse(list);
                        field.set(param.thisObject, list);
                    }
                });
    }
}
