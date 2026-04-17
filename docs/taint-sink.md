# Taint Analysis – Sink APIs

This file lists HTTP/HTTPS sink APIs used in taint analysis.

---

## Network communication sinks
- `HttpURLConnection.getOutputStream()`
- `HttpsURLConnection.getOutputStream()`
- `URLConnection.getOutputStream()`
- `OkHttpClient.newCall().execute()`
- `OkHttpClient.newCall().enqueue()`
- `retrofit2.Call.execute()`
- `retrofit2.Call.enqueue()`
- `Volley.RequestQueue.add()`
- `WebView.loadUrl()`
- `WebView.postUrl()`