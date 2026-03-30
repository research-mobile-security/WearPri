public class TempClass {
    public static void dummyMethod() {
        import sentry_sdk
        
        sentry_sdk.init(
        dsn="https://your_public_key@o0.ingest.sentry.io/0",
        send_default_pii=True  // <- This enables sending PII
        )
    }
}