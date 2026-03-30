public class TempClass {
    public static void dummyMethod() {
        // users opted out of tracking by default
        optOutTrackingDefault = true;
        trackAutomaticEvents = false;
        MixpanelAPI mixpanel =
        MixpanelAPI.getInstance(context, 'YOUR_PROJECT_TOKEN', optOutTrackingDefault, trackAutomaticEvents);
        
        
        // this track call will not work
        mixpanel.track('some_event');
        
        // opt user back into tracking
        mixpanel.optInTracking();
        
        // this track call will work
        mixpanel.track('some_other_event');
    }
}