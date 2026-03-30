public class TempClass {
    public static void dummyMethod() {
        cleverTap.setOptOut(true);
        cleverTap.enableDeviceNetworkInfoReporting(false);
        profileUpdate.put("MSG-email", false); // Disable email notifications
        profileUpdate.put("MSG-push", true); // Enable push notifications
        profileUpdate.put("MSG-sms", false); // Disable SMS notifications
        profileUpdate.put(“MSG-push-all”, false); //Disable push notifications for all devices
        cleverTap.profile.push(profileUpdate);
    }
}