public class TempClass {
    public static void dummyMethod() {
        // If the user opts out of targeted advertising:
        MetaData gdprMetaData = new MetaData(this);
        gdprMetaData.set("gdpr.consent", false);
        gdprMetaData.commit();
    }
}