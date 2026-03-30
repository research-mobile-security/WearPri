public class TempClass {
    public static void dummyMethod() {
        android {
        defaultConfig {
        ...
        // Add this block. If an ext block already exists, simply add the below property.
        // If the property already exists, make sure it's set to false.
        ext {
        heapEnabled = false
        }
        }
        }
    }
}