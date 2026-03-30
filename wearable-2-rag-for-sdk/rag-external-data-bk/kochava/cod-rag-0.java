Tracker.getInstance().setAppLimitAdTracking(true);
// create the privacy profile
Tracker.getInstance().registerPrivacyProfile("restrict ids", new String[]{"adid", "android_id"});

// enable the privacy profile for this user
Tracker.getInstance().setPrivacyProfileEnabled("restrict ids", true);

// start the tracker
Tracker.getInstance().startWithAppGuid(context, "_YOUR_APP_GUID_");