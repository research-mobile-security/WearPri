public class TempClass {
    public static void dummyMethod() {
        Ogury.setPrivacyData("us_optout", true);
        Ogury.setPrivacyData("us_optout_partner", true);
        Ogury.applyChildPrivacy(OguryChildPrivacyTreatment.UNDER_AGE_OF GDPR_CONSENT_TREATMENT_TRUE);
    }
}