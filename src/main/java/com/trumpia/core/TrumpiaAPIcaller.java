package com.trumpia.core;

import com.trumpia.user.UserAccount;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*This file is part of Sign-up Page Sample.

The Sign-up Page Sample is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

The Sign-up Page Sample is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with The Sign-up Page Sample.  If not, see <http://www.gnu.org/licenses/>. 
*/
public class TrumpiaAPIcaller {

    private static OkHttpClient client = new OkHttpClient();

    /**
     * { //PUT method
     * "mobile_number" : "2003004000"
     * "message" : "Thank you for joining! The verification code is : [$code]"
     * "char_type" : "1"
     * "length" : "4"
     * "valid_period" : "10"
     * "country_code" : "1"
     * }
     *
     * @param mobileNumber
     * @param user
     * @return JSONObject
     * @see <a href="https://trumpia.com/api/docs/rest/functions/authentication.php">Trumpia API refrence</a>
     */
    public static JSONObject auth(String mobileNumber, UserAccount user) throws IOException {

        final String requestUrl = "http://api.trumpia.com/rest/v1/" + user.getUserId() + "/authentication/sms";

        JSONObject payload = new JSONObject();
        payload.put("mobile_number", mobileNumber);
        payload.put("message", "Thank you for joining! The verification code is : [$code]");
        payload.put("char_type", 1);
        payload.put("length", 4);
        payload.put("valid_period", 10);
        payload.put("country_code", 1);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, payload.toString());

        Request request = new Request.Builder()
                .url(requestUrl)
                .put(body)
                .addHeader("x-apikey", user.getApikey())
                .addHeader("content-type", "application/json")
                .build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        if (responseBody == null)
            return null;

        return new JSONObject(responseBody.string());
    }

    /**
     * @param requestId
     * @param user
     * @return
     * @throws IOException
     * @see <a href="https://trumpia.com/api/docs/rest/functions/verification.php">Trumpia API refrence</a>
     */
    public static JSONObject report(String requestId, UserAccount user) throws IOException {

        final String requestUrl = "http://api.trumpia.com/rest/v1/" + user.getUserId() + "/report/" + requestId;

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("x-apikey", user.getApikey())
                .addHeader("content-type", "application/json")
                .build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        if (responseBody == null)
            return null;

        return new JSONObject(responseBody.string());

    }

    /**
     * { //sample JSON body
     * code: 1234
     * request_date : "2017-12-01 12:00:00"
     * }
     *
     * @param verifyCode
     * @param authId
     * @param user
     * @throws IOException
     * @see <a href="https://trumpia.com/api/docs/rest/functions/report.php">Trumpia API refrence</a>
     */
    public static JSONObject verify(String verifyCode, String authId, UserAccount user) throws IOException {

        final String requestUrl = "http://api.trumpia.com/rest/v1/" + user.getUserId() + "/verification/sms/" + authId;

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(dateFormat.format(date));

        JSONObject payload = new JSONObject();
        payload.put("code", verifyCode);
        payload.put("request_date", dateFormat.format(date));
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, payload.toString());

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("x-apikey", user.getApikey())
                .addHeader("content-type", "application/json")
                .build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        if (responseBody == null)
            return null;

        return new JSONObject(responseBody.string());
    }
}	
