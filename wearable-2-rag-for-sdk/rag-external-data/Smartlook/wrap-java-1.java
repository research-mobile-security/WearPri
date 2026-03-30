public class TempClass {
    public static void dummyMethod() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpExtKt.addSmartlookInterceptor(builder, new SmartlookOkHttpInterceptor());
        OkHttpClient client = builder.build();
    }
}