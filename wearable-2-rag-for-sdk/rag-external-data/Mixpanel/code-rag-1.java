optOutTrackingDefault = true;
trackAutomaticEvents = false;
MixpanelAPI mixpanel =
    MixpanelAPI.getInstance(context, 'YOUR_PROJECT_TOKEN', optOutTrackingDefault, trackAutomaticEvents);
mixpanel.track('some_event');
mixpanel.optInTracking();
mixpanel.track('some_other_event');