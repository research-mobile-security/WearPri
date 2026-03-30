public class TempClass {
    public static void dummyMethod() {
        MoESdkStateHelper.disableDataTracking(context);
        MoESdkStateHelper.disableSdk(context);
        MoECoreHelper.INSTANCE.deleteUser(context,listener);
    }
}