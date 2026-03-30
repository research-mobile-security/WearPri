public class TempClass {
    public static void dummyMethod() {
        Configuration config = Configuration.load(this);
        config.setGenerateAnonymousId(false);
        Bugsnag.start(this, config);
    }
}