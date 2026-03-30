public class TempClass {
    public static void dummyMethod() {
        Configuration config = Configuration.load(this);
        config.setAutoTrackSessions(false);
        Bugsnag.start(this, config);
    }
}