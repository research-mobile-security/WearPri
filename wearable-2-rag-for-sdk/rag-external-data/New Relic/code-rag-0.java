NewRelic.disableFeature(FeatureFlag.DefaultInteractions);
NewRelic.enableFeature(FeatureFlag.CrashReporting);
NewRelic.withApplicationToken(NEW_RELIC_TOKEN).start(this.getApplication());
