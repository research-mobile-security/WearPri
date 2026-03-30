@Override
public void onCreate() {
  super.onCreate(); 
  Localytics.autoIntegrate(this);
  if (!isUserOptedIntoDataCollection()) {
    Localytics.setOptedOut(true);
  }
}
          
