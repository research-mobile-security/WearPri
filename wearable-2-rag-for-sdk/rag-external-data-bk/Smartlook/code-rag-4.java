Set<SmartlookMaskUrlInterceptor.Mask> masks = Set.of(
  new SmartlookMaskUrlInterceptor.Mask(
    "(secret=)[^&]+(&*)",
    "$1<hidden>$2"
  )
);

OkHttpClient.Builder builder = new OkHttpClient.Builder();
OkHttpExtKt.addSmartlookInterceptor(builder, new SmartlookMaskUrlInterceptor(masks));
OkHttpClient client = builder.build();
