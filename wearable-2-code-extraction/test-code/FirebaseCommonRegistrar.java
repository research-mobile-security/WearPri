// 
// Decompiled by Procyon v0.6.0
// 

//package com.google.firebase;

//import com.google.firebase.platforminfo.KotlinDetector;
//import android.os.Build;
//import com.google.firebase.platforminfo.LibraryVersionComponent;
//import com.google.firebase.heartbeatinfo.DefaultHeartBeatController;
//import com.google.firebase.platforminfo.DefaultUserAgentPublisher;
//import java.util.ArrayList;
//import com.google.firebase.components.Component;
//import java.util.List;
//import android.os.Build$VERSION;
//import android.content.pm.ApplicationInfo;
//import android.content.Context;
//import com.google.firebase.components.ComponentRegistrar; 

public class FirebaseCommonRegistrar implements ComponentRegistrar
{
    private static final String ANDROID_INSTALLER = "android-installer";
    private static final String ANDROID_PLATFORM = "android-platform";
    private static final String DEVICE_BRAND = "device-brand";
    private static final String DEVICE_MODEL = "device-model";
    private static final String DEVICE_NAME = "device-name";
    private static final String FIREBASE_ANDROID = "fire-android";
    private static final String FIREBASE_COMMON = "fire-core";
    private static final String KOTLIN = "kotlin";
    private static final String MIN_SDK = "android-min-sdk";
    private static final String TARGET_SDK = "android-target-sdk";
    
    private static String safeValue(final String s) {
        return s.replace(' ', '_').replace('/', '_');
    }
    
    @Override
    public List<Component<?>> getComponents() {
        final ArrayList list = new ArrayList();
        list.add(DefaultUserAgentPublisher.component());
        list.add(DefaultHeartBeatController.component());
        list.add(LibraryVersionComponent.create("fire-android", String.valueOf(Build$VERSION.SDK_INT)));
        list.add(LibraryVersionComponent.create("fire-core", "21.0.0"));
        list.add(LibraryVersionComponent.create("device-name", safeValue(Build.PRODUCT)));
        list.add(LibraryVersionComponent.create("device-model", safeValue(Build.DEVICE)));
        list.add(LibraryVersionComponent.create("device-brand", safeValue(Build.BRAND)));
        list.add(LibraryVersionComponent.fromContext("android-target-sdk", new FirebaseCommonRegistrar$$ExternalSyntheticLambda0()));
        list.add(LibraryVersionComponent.fromContext("android-min-sdk", new FirebaseCommonRegistrar$$ExternalSyntheticLambda1()));
        list.add(LibraryVersionComponent.fromContext("android-platform", new FirebaseCommonRegistrar$$ExternalSyntheticLambda2()));
        list.add(LibraryVersionComponent.fromContext("android-installer", new FirebaseCommonRegistrar$$ExternalSyntheticLambda3()));
        final String detectVersion = KotlinDetector.detectVersion();
        if (detectVersion != null) {
            list.add(LibraryVersionComponent.create("kotlin", detectVersion));
        }
        return list;
    }
}
