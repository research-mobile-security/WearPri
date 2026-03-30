public static void getConsents(final AdobeCallback<Map<String, Object>> callback);
Consent.getConsents(new AdobeCallback<Map<String, Object>>() {
    @Override
    public void call(Map<String, Object> currentConsents) {
        if (currentConsents == null) { return; }
    final Map<String, Object> consents = currentConsets.get("consents");
    final Map<String, Object> collectConsent = consents.get("collect");
    final String collectConsentStatus = collectConsent.get("val");
    // inspect collectConsentStatus
    }
});
