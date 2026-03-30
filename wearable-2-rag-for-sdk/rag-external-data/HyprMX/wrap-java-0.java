public class TempClass {
    public static void dummyMethod() {
        HyprMX.INSTANCE.setConsentStatus(ConsentStatus.CONSENT_DECLINED); // If user declined consent, set this to CONSENT_DECLINED
        HyprMX.INSTANCE.setAgeRestrictedUser(true); // Set this to true if the user is under 16 or requires child-directed treatment
    }
}