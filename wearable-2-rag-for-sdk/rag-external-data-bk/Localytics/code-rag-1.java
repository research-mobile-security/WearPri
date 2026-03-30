//Code from Application class
@Override
public void onCreate() {
  super.onCreate();
    
  Localytics.autoIntegrate(this);
  // The following method should reference a boolean value in your app that determines if the user has opted into
  // data collection or not. The default (if never asked) should be false.
  if (!isUserOptedIntoDataCollection()) {
    Localytics.setOptedOut(true);
  }
}
          
