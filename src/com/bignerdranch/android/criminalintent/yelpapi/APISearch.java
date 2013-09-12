package com.bignerdranch.android.criminalintent.yelpapi;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;


public class APISearch {
    private static final String TAG = "RestFrag";

    // Define your keys, tokens and secrets.  These are available from the Yelp website.

    private static final String CONSUMER_KEY = "zZ8PCCX4YOzsHlbvCWwPnw";
    private static final String CONSUMER_SECRET = "xUS5cVPM4f8bJglXQ1O0NS2ssLU";
    private static final String TOKEN = "1Tf5UkpQn0vf9lFla8bTQfcjLq__tkND";
    private static final String TOKEN_SECRET = "MNaRY1NcMUU9DuyMiW9u6s07Bfc";

    //team yelp api access
//    private static final String CONSUMER_KEY = "RyZXgFghTEKhwmCp5sH6dA";
//    private static final String CONSUMER_SECRET = "aUpRy0DQ6o5l1JEWQ8bXwHK-tcQ";
//    private static final String TOKEN = "u7qCZDKy-d1oppJts-yEUZyE769K7gxa";
//    private static final String TOKEN_SECRET = "Ol5Lp4AfnpgE_ORr8Tr7f4V7Zro";

    // Some example values to pass into the Yelp search service.
    //    String lat = "30.361471";
    //    String lng = "-87.164326";
    //    String city = "Chicago";
    //    String restaurant = "Girl & The Goat";

    String mCategory = "restaurants";
    String mSearchResultLimit = "1";

//    private YelpSearchResult places;

    String resName;
    String resPhone;
    String resURL;
    String yelpStreet;
    String resStreet;
    String resCity;
    String resState;
    String resZIP;
    String resAddress;

    public void search(String restaurant, String city) throws JSONException {

        // Execute a signed call to the Yelp service.
        OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
        Token accessToken = new Token(TOKEN, TOKEN_SECRET);
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");

        request.addQuerystringParameter("location", city);
        request.addQuerystringParameter("category", mCategory);
        request.addQuerystringParameter("term", restaurant);
        request.addQuerystringParameter("limit", mSearchResultLimit);

        service.signRequest(accessToken, request);
        Response response = request.send();
        String rawData = response.getBody();
//        Log.d(TAG, rawData);

        JSONObject yelpData = null;
        try {
            yelpData = new JSONObject(rawData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray yelpReponse = yelpData.getJSONArray("businesses");

        resName = yelpReponse.getJSONObject(0).get("name").toString();
        resPhone = yelpReponse.getJSONObject(0).get("phone").toString();
        resURL = yelpReponse.getJSONObject(0).get("url").toString();
        yelpStreet = yelpReponse.getJSONObject(0).getJSONObject("location").get("address").toString();
        resStreet = yelpStreet.substring(2, yelpStreet.length() - 2);
        resCity = yelpReponse.getJSONObject(0).getJSONObject("location").get("city").toString();
        resState = yelpReponse.getJSONObject(0).getJSONObject("location").get("state_code").toString();
        resZIP = yelpReponse.getJSONObject(0).getJSONObject("location").get("postal_code").toString();
        resAddress = resStreet + " " + resCity + " " + resState + " " + resZIP;

    }

    public String getResName() {
        return resName;
    }

    public String getResAddress() {
        return resAddress;
    }

    public String getResPhone() {
        return resPhone;
    }


    public String getResURL() {
        return resURL;
    }


    public String getResStreet() {
        return resStreet;
    }


    public String getResCity() {
        return resCity;
    }

    public String getResState() {
        return resState;
    }

    public String getResZIP() {
        return resZIP;
    }

}

