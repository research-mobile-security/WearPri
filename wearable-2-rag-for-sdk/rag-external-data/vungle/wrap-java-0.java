public class TempClass {
    public static void dummyMethod() {
        // Usage example of GDPR API
        // To set the user's consent status to opted out:
        VunglePrivacySettings.setGDPRStatus(false, "1.0.0");
        
        // To find out what the user's current consent status is:
        // This will return unknown if the GDPR Consent status has not been set
        // Otherwise, it will return "opted_out" or "opted_in"
        String currentGDPRStatus = VunglePrivacySettings.getGDPRStatus();
        String consentMessageVersion = VunglePrivacySettings.getGDPRMessageVersion();
    }
}