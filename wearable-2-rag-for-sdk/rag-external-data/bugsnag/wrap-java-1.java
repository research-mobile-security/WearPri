public class TempClass {
    public static void dummyMethod() {
        Configuration config = Configuration.load(this);
        config.clearMetadata("account");
        // or
        config.clearMetadata("account", "name");
        Bugsnag.start(this, config);
    }
}