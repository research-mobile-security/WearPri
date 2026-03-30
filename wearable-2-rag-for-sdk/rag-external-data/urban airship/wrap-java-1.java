public class TempClass {
    public static void dummyMethod() {
        UAirship.shared().getPrivacyManager().enable(PrivacyManager.Feature.PUSH, PrivacyManager.Feature.ANALYTICS);
    }
}