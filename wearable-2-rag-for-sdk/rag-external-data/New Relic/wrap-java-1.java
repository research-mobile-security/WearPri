public class TempClass {
    public static void dummyMethod() {
        NewRelic.withApplicationToken(NEW_RELIC_TOKEN)
        .withDefaultInteractions(false)
        .withCrashReportingEnabled(true)
        .start(this.getApplication());
    }
}