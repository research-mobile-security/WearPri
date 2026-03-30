public class TempClass {
    public static void dummyMethod() {
        AirshipConfigOptions options = AirshipConfigOptions.newBuilder()
        // ...
        .setEnabledFeatures(PrivacyManager.Feature.NONE)
        .build();
    }
}