public class TempClass {
    public static void dummyMethod() {
        new Marigold().setGeoIpTrackingEnabled(false);
        
        // with result handler
        new Marigold().setGeoIpTrackingEnabled(false, new Marigold.MarigoldHandler<Void>() {
        @Override
        public void onSuccess(Void value) {
        // handle success
        }
        
        @Override
        public void onFailure(Error error) {
        // handle error
        }
        });
    }
}