new Marigold().setGeoIpTrackingEnabled(false);
new Marigold().setGeoIpTrackingEnabled(false, new Marigold.MarigoldHandler<Void>() {
    @Override
    public void onSuccess(Void value) {
    }
    @Override
    public void onFailure(Error error) {
    }
});
