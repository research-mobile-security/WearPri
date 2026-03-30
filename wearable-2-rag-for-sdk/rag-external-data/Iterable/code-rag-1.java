JSONObject store_address = new JSONObject();
final JSONObject dataFields = new JSONObject();

try {
    store_address.put("Street1", "123 Main St");
    store_address.put("Street2", "Apt 1");
    store_address.put("City", "Iter-a-ville");
    store_address.put("State", "CA");
    store_address.put("Zip", "90210");
    datafields.put("dataFields", store_address);
} catch (JSONException e) {
    e.printStackTrace();
}
CommerceItem item = new CommerceItem(
    "TOY1",
    "Red Racecar",
    4.99,
    1,
    "RR123",
    "A small, red racecar",
    "https://www.example.com/toys/racecar",
    "https://www.example.com/toys/racecar/images/car.png",
    new String[] {"Toy", "Inexpensive" }
);

List<CommerceItem> items = new ArrayList<CommerceItem>();
items.add(item);
IterableApi.getInstance().trackPurchase(new Double(4.99), items, dataFields);