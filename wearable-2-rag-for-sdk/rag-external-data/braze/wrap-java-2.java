public class TempClass {
    public static void dummyMethod() {
        fun setDeviceObjectAllowlist(deviceObjectAllowlist: EnumSet<DeviceKey>): BrazeConfig.Builder
        fun setDeviceObjectAllowlistEnabled(enabled: Boolean): BrazeConfig.Builder
        abstract fun setGoogleAdvertisingId(googleAdvertisingId: String, isLimitAdTrackingEnabled: Boolean)
    }
}