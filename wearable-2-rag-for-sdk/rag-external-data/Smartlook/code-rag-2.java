OkHttpClient.Builder builder = new OkHttpClient.Builder();
OkHttpExtKt.addSmartlookInterceptor(builder, new SmartlookNonBinaryBodyInterceptor());
OkHttpClient client = builder.build();
