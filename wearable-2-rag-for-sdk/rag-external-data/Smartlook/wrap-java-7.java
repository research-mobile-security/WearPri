public class TempClass {
    public static void dummyMethod() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        
        OkHttpExtKt.addSmartlookInterceptor(builder, new InterceptorA());
        OkHttpExtKt.addSmartlookInterceptor(builder, new InterceptorB());
        OkHttpExtKt.addSmartlookInterceptor(builder, new InterceptorC());
        
        OkHttpClient client = builder.build();
    }
}