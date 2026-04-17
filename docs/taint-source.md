# Taint Analysis – Source APIs

This file lists source APIs used in taint analysis.

---

## Device or other IDs
- `TelephonyManager.getDeviceId()`
- `TelephonyManager.getImei()`
- `TelephonyManager.getMeid()`
- `Settings.Secure.getString(..., ANDROID_ID)`
- `WifiInfo.getMacAddress()`
- `NetworkInterface.getHardwareAddress()`
- `MediaDrm.getPropertyByteArray("deviceUniqueId")`
- `FirebaseInstallations.getId()`
- `AdvertisingIdClient.getAdvertisingIdInfo().getId()`
- `WifiManager.getConnectionInfo().getIpAddress()`
- `InetAddress.getLocalHost().getHostAddress()`

---

## Personal info
- `ContactsContract.Profile.DISPLAY_NAME`
- `ContactsContract.Profile.CONTENT_URI`
- `AccountManager.getAccounts()`
- `ContactsContract.CommonDataKinds.Email`
- `TelephonyManager.getLine1Number()`
- `ContactsContract.CommonDataKinds.Phone.NUMBER`
- `GoogleSignInAccount.getId()`
- `SharedPreferences.getString("user_id", ...)`
- `ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY`
- `AccessibilityService.onAccessibilityEvent()`
- `EditText.getText().toString()`
- `WebChromeClient.onJsPrompt()`

---

## Location
- `LocationManager.getLastKnownLocation()`
- `Location.getLatitude()`
- `Location.getLongitude()`
- `Location.getAltitude()`
- `FusedLocationProviderClient.getLastLocation()`

---

## Health & fitness
- `Sensor.TYPE_HEART_RATE`
- `SensorEvent.values`
- `GoogleFit.HistoryApi.readData()`

---

## App info & performance
- `ActivityManager.getRunningAppProcesses()`
- `Debug.getMemoryInfo()`
- `BatteryManager.getIntProperty()`

---

## Messages
- `SmsMessage.getMessageBody()`
- `Telephony.Sms.Inbox`
- `Telephony.Mms`
- `EmailContract.Message`

---

## Financial info
- `BillingClient.queryPurchases()`
- `BillingClient.queryPurchaseHistoryAsync()`
- `getAccountNumber()`
- `getTransactionHistory()`
- `getPaymentMethod()`

---

## Photos or video
- `MediaStore.Images.Media.getContentUri()`
- `MediaStore.Video.Media.getContentUri()`
- `Camera.onPictureTaken()`

---

## Audio files
- `MediaStore.Audio.Media`
- `MediaRecorder.OnInfoListener`
- `AudioRecord.read()`

---

## Files and docs
- `FileInputStream.read()`
- `DocumentFile.fromSingleUri()`
- `DocumentFile.fromTreeUri()`
- `ContentResolver.openInputStream()`

---

## Calendar
- `CalendarContract.Events`
- `CalendarContract.Instances`
- `CalendarContract.Reminders`

---

## Contacts
- `ContactsContract.Contacts`
- `ContactsContract.CommonDataKinds.Phone`
- `CallLog.Calls`

---

## App activity
- `UsageStatsManager.queryUsageStats()`
- `AccessibilityService.onAccessibilityEvent()`
- `ActivityManager.getRunningTasks()`

---

## Web browsing
- `Browser.BOOKMARKS_URI`
- `CookieManager.getCookie()`
- `WebView.getOriginalUrl()`