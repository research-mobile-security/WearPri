public class TempClass {
    public static void dummyMethod() {
        // initialize the Smaato SDK
        SmaatoSdk.init(this, "SMAATO_PUBLISHER_ID");
        //Set LGPD consent
        SmaatoSdk.setLgpdConsentEnabled(true);
    }
}