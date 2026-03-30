public class TempClass {
    public static void dummyMethod() {
        Heap.startRecording(
        context,
        "YOUR_APP_ID",
        new Options.Builder()
        .setShouldCaptureAdvertiserId(true)
        .setShouldCaptureVendorId(true)
        .build()
        )
    }
}