public class TempClass {
    public static void dummyMethod() {
        CheckoutConfiguration(
        environment = environment,
        clientKey = clientKey,
        analyticsConfiguration = AnalyticsConfiguration(AnalyticsLevel.NONE), // Set to AnalyticsLevel.NONE to not send analytics data to Adyen.
        // Other configuration...
        )
    }
}