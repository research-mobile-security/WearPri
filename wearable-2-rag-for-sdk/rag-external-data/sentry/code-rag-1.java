import io.sentry.Sentry;
Sentry.init(options -> {
  options.setBeforeSend((event, hint) -> {
    event.setServerName(null); 
    return event;
  });
});
