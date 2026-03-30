public class TempClass {
    public static void dummyMethod() {
        MobileFusePrivacyPreferences privacyPrefs = new MobileFusePrivacyPreferences.Builder()
        .setGppConsentString("DBACNYA~CPXxRfAPXxRfAAfKABENB-CgAAAAAAAAAAYgAAAAAAAA~1YNN")
        .build();
        
        MobileFuse.setPrivacyPreferences(privacyPrefs);
    }
}