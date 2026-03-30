public class TempClass {
    public static void dummyMethod() {
        Adjust.gdprForgetMe();
        AdjustThirdPartySharing adjustThirdPartySharing = new AdjustThirdPartySharing(true);
        Adjust.trackThirdPartySharing(adjustThirdPartySharing);
    }
}