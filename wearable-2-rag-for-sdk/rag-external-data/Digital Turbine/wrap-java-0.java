public class TempClass {
    public static void dummyMethod() {
        OfferWall.setConsent(OfferWallPrivacyConsent.GDPR(true))
        OfferWall.start(activity, appId, offerWallListener, true);
    }
}