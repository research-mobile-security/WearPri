public class TempClass {
    public static void dummyMethod() {
        DataUseConsent dataUseConsent = new GDPR(GDPR.GDPR_CONSENT.BEHAVIORAL);
        Chartboost.addDataUseConsent(context, dataUseConsent);
    }
}