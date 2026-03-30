MobileFusePrivacyPreferences privacyPrefs = new MobileFusePrivacyPreferences.Builder()
    .setSubjectToCoppa(false) 
    .setUsPrivacyConsentString("<US Privacy String>") 
    .build();
MobileFuse.setPrivacyPreferences(privacyPrefs);
