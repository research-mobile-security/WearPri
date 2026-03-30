public class TempClass {
    public static void dummyMethod() {
        MobileFusePrivacyPreferences privacyPrefs = new MobileFusePrivacyPreferences.Builder()
        .setSubjectToCoppa(false) // should be true if user is under 13 years of age
        .setUsPrivacyConsentString("<US Privacy String>") // e.g. 1YNN
        .build();
        
        MobileFuse.setPrivacyPreferences(privacyPrefs);
    }
}