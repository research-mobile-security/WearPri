public static class CustomInterceptor extends SmartlookOkHttpInterceptor {
  @Override
  public SmartlookNetworkRequest onIntercept(
    @NonNull SmartlookChain original,
    @NonNull SmartlookNetworkRequest intercepted
  ) {
    // Process original and modify intercepted
    return intercepted;
  }
}
