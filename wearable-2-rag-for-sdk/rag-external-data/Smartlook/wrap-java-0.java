public class TempClass {
    public static void dummyMethod() {
        SmartlookNetworkRequest networkRequest = new SmartlookNetworkRequest(
        1000,
        new URL("https://www.example.com"),
        "POST",
        "http/1.1",
        "OkHttp",
        SmartlookNetworkRequest.Status.OK,
        200,
        false,
        "{\"example\":\"body\"}",
        "{\"example\":\"body\"}",
        new HashMap<String, List<String>>() {{
        put("sample", new ArrayList<String>() {{
        add("header");
        }});
        }},
        new HashMap<>()
        );
        
        Smartlook.getInstance().trackNetworkRequest(networkRequest);
    }
}