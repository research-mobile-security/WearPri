public class TempClass {
    public static void dummyMethod() {
        Batch.updateAutomaticDataCollection {
        it.apply {
        setGeoIPEnabled(true) // Enable GeoIP resolution on server side
        setDeviceBrandEnabled(true) // Enable automatic collection of the device brand information
        setDeviceModelEnabled(true) // Enable automatic collection of the device model information
        }
        }
    }
}