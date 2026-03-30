Tracker.getInstance().setAppLimitAdTracking(true);
Tracker.getInstance().registerPrivacyProfile("restrict ids", new String[]{"adid", "android_id"});
Tracker.getInstance().setPrivacyProfileEnabled("restrict ids", true);
Tracker.getInstance().startWithAppGuid(context, "_YOUR_APP_GUID_");