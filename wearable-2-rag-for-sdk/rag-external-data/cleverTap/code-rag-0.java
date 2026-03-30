cleverTap.setOptOut(true);
cleverTap.enableDeviceNetworkInfoReporting(false);
profileUpdate.put("MSG-email", false); 
profileUpdate.put("MSG-push", true); 
profileUpdate.put("MSG-sms", false); 
profileUpdate.put(“MSG-push-all”, false); 
cleverTap.profile.push(profileUpdate);
