public class TempClass {
    public static void dummyMethod() {
        configuration.setDeviceIdSource(Blueshift.DeviceIdSource.GUID);
        Blueshift.setTrackingEnabled(context, false);
    }
}