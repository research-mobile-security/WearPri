public LocationClient mLocationClient = null;
private MyLocationListener myListener = new MyLocationListener();
public void onCreate() {
        LocationClient.setAgreePrivacy(false);
}