GfpSdk.setUserProperties(
    GfpSdk.getUserProperties()
        .buildUpon()
        .childDirectedTreatment(true)
        .underAgeOfConsent(true)
        .build()
);