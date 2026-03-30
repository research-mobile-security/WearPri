MobileFusePrivacyPreferences privacyPrefs = new MobileFusePrivacyPreferences.Builder()
    .setDoNotTrack(true) // With "do not track" set, the user is opted out!
    .build();

MobileFuse.setPrivacyPreferences(privacyPrefs);
