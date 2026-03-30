import io.sentry.Sentry;

Sentry.init(options -> {
  options.setBeforeSend((event, hint) -> {
    // Modify the event here:
    event.setServerName(null); // Don't send server names.
    return event;
  });
});
