public class TempClass {
    public static void dummyMethod() {
        // set before you build
        builder.autoTrackDeviceAttributes(false)
        // Future calls to the SDK are anonymous
        CustomerIO.instance().clearIdentify()
    }
}