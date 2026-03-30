Configuration config = Configuration.load(this);
config.clearMetadata("account");
config.clearMetadata("account", "name");
Bugsnag.start(this, config);
