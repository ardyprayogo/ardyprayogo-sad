package com.app.ardyprayogo_sad.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class VolleyUtil {
    private static String baseUrl = "https://randomuser.me/api?results=5&exc=login,registered,i";

    public static void sendGetRequest(Context context, final VolleyResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, baseUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                        Log.d("API", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            String data = new String(networkResponse.data);
                            try {
                                String message = new JSONObject(data).getString("message");
                                listener.onError(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                listener.onError(error.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

}
