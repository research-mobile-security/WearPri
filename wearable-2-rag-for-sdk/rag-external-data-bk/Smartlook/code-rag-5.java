Set<SmartlookMaskBodyInterceptor.Mask> masks = Set.of(
  new SmartlookMaskBodyInterceptor.Mask(
    "\"sensitive\"[:]\\s(\".*\")",
    "sensitive: <hidden>"
  )
);

OkHttpClient.Builder builder = new OkHttpClient.Builder();
OkHttpExtKt.addSmartlookInterceptor(builder, new SmartlookMaskBodyInterceptor(masks));
OkHttpClient client = builder.build();
