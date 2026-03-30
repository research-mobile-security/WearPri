public class TempClass {
    public static void dummyMethod() {
        @Override
        public void onCreate() {
        String chartbeatAccountId = "12345";
        String chartbeatSiteId = "mysite.com";
        
        super.onCreate();
        Tracker.setupTracker(chartbeatAccountId, chartbeatSiteId, this, true);
        }
    }
}