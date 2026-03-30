public class TempClass {
    public static void dummyMethod() {
        //Java
        Instabug.getUserUUID((uuid)->{
        //use the uuid
        });
        
        //Kotlin
        Instabug.getUserUUID { uuid ->
        //use the uuid
        }
    }
}
