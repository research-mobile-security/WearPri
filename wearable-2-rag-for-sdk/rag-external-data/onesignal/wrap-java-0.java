public class TempClass {
    public static void dummyMethod() {
        OneSignal.setConsentRequired(true);
        // Enable your app to share location with OneSignal
        OneSignal.getLocation().setShared(true);
        // Returns true if your app is sharing location with OneSignal
        boolean isShared = OneSignal.Location.isShared();
    }
}