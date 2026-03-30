public class TempClass {
    public static void dummyMethod() {
        appsflyer.start(getApplicationContext(), null, new AppsFlyerRequestListener() {
        @Override
        public void onSuccess() {
        Log.d(LOG_TAG, "Launch sent successfully, got 200 response code from server");
        appsflyer.stop(true, getApplicationContext());
        }
        
        @Override
        public void onError(int i, @NonNull String s) {
        Log.d(LOG_TAG, "Launch failed to be sent:\n" +
        "Error code: " + i + "\n"
        + "Error description: " + s);
        }
        });
    }
}