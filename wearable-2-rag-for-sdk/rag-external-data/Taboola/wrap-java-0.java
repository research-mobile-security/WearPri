public class TempClass {
    public static void dummyMethod() {
        HashMap<String, String> extraProperties = new HashMap<String, String>() {{ put("cex", "true"); }};
        Taboola.setGlobalExtraProperties(extraProperties);
    }
}