Configuration config = Configuration.load(this);
config.setGenerateAnonymousId(false);
Bugsnag.start(this, config);
