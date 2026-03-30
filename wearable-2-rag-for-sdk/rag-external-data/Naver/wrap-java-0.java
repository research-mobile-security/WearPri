public class TempClass {
    public static void dummyMethod() {
        GfpSdk.setUserProperties(
        GfpSdk.getUserProperties()
        .buildUpon()
        .childDirectedTreatment(true)
        .underAgeOfConsent(true)
        .build()
        );
    }
}