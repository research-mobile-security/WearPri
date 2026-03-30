public class TempClass {
    public static void dummyMethod() {
        Set<String> masks = Set.of("Example-Header", "Accept.*");
        
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpExtKt.addSmartlookInterceptor(builder, new SmartlookHeadersInterceptor(masks));
        OkHttpClient client = builder.build();
    }
}