public class TempClass {
    public static void dummyMethod() {
        //send "some_event"
        mixpanel.track('some_event');
        
        // opt user out of tracking
        // SDK is prevented from sending any data
        mixpanel.optOutTracking();
        
        // this track call will not work
        mixpanel.track('some_other_event');
    }
}