MobileFusePrivacyPreferences privacyPrefs = new MobileFusePrivacyPreferences.Builder()
    .setDoNotTrack(true) 
    .build();
MobileFuse.setPrivacyPreferences(privacyPrefs);
