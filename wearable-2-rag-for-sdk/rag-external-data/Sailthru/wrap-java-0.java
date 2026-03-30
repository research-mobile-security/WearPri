public class TempClass {
    public static void dummyMethod() {
        // must be called before startEngine
        new Marigold().setGeoIpTrackingDefault(false);
    }
}