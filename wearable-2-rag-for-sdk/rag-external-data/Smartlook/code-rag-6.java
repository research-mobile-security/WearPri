public static class CustomInterceptor extends SmartlookOkHttpInterceptor {
  @Override
  public SmartlookNetworkRequest onIntercept(
    @NonNull SmartlookChain original,
    @NonNull SmartlookNetworkRequest intercepted
  ) {
    return intercepted;
  }
}
