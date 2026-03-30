public class TempClass {
    public static void dummyMethod() {
        val dataCollector = DataCollector(context, authorization)
        val dataCollectorRequest = DataCollectorRequest(hasUserLocationConsent)
        
        dataCollector.collectDeviceData(context, dataCollectorRequest) { result ->
        if (result is DataCollectorResult.Success) {
        // send result.deviceData to your server
        }
        }
    }
}