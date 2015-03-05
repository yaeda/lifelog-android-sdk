package com.kazeor.lifelog;

import android.content.Context;

import com.google.gson.JsonObject;
import com.kazeor.lifelog.model.Token;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

public class Lifelog {

    public static class Scope {
        public static final byte READ_PROFILE    = 0x01;
        public static final byte READ_ACTIVITIES = 0x02;
        public static final byte READ_LOCATIONS  = 0x04;
        protected static final String LABEL_READ_POFILE = "lifelog.profile.read";
        protected static final String LABEL_READ_ACTIVITIES = "lifelog.activities.read";
        protected static final String LABEL_READ_LOCATIONS = "lifelog.locations.read";
    }

    public static class Type {
        public static final String APPLICATION = "application";
        public static final String CAMERA      = "camera";
        public static final String MUSIC       = "music";
        public static final String PHYSICAL    = "physical";
        public static final String SLEEP       = "sleep";
        public static final String TRANSPORT   = "transport";
    }

    public static class SubType {
        public static final String BOOKS         = "books";
        public static final String BROWSING      = "browsing";
        public static final String CAMERA_ALBUM  = "camera/album";
        public static final String COMMUNICATION = "communication";
        public static final String GAME          = "game";
        public static final String MOVIE_TV      = "movie/tv";
        public static final String WALK          = "walk";
        public static final String RUN           = "run";
        public static final String BICYCLE       = "bicycle";
        public static final String OTHER         = "other";
    }

    public Lifelog(Context context) {
        if (context != null) {
            this.context = context.getApplicationContext();
        }
    }

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public void setCredential(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void setScope(int scope) {
        this.scope = (scope & Scope.READ_PROFILE) == Scope.READ_PROFILE ?
                Scope.LABEL_READ_POFILE : "";
        if ((scope & Scope.READ_ACTIVITIES) == Scope.READ_ACTIVITIES) {
            this.scope += this.scope.length() == 0 ?
                    Scope.LABEL_READ_ACTIVITIES : "+" + Scope.LABEL_READ_ACTIVITIES;
        }
        if ((scope & Scope.READ_LOCATIONS) == Scope.READ_LOCATIONS) {
            this.scope += this.scope.length() == 0 ?
                    Scope.LABEL_READ_LOCATIONS : "+" + Scope.LABEL_READ_LOCATIONS;
        }
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void getAccessToken(final String code, final TokenCallback callback) {
        if (this.clientId == null || this.clientSecret == null) {
            callback.onError(0, "client id or secret is not initialized");
            return;
        }
        if (code == null || callback == null) {
            callback.onError(0, "invalid argument");
            return;
        }

        Ion.with(context)
                .load(ENDPOINT_GET_ACCESS_TOKEN)
                .setBodyParameter("client_id", this.clientId)
                .setBodyParameter("client_secret", this.clientSecret)
                .setBodyParameter("grant_type", "authorization_code")
                .setBodyParameter("code", code)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        int code = result.getHeaders().code();
                        if (e != null) {
                            callback.onError(code, "Something went wrong");
                        } else {
                            if (code == 200) {
                                // In this hello world example we only use the access token.
                                String accessToken = result.getResult().get("access_token").getAsString();
                                String refreshToken = result.getResult().get("refresh_token").getAsString();
                                Token token = new Token(accessToken, refreshToken);
                                callback.onSuccess(token);
                            } else {
                                callback.onError(code, "Request failed, got a " + code);
                            }
                        }
                    }
                });
    }

    public void refreshToken(final TokenCallback callback) {
        if (this.clientId == null || this.clientSecret == null || this.token == null) {
            callback.onError(0, "client id, secret or token is not initialized");
            return;
        }
        if (callback == null) {
            callback.onError(0, "invalid argument");
            return;
        }

        Ion.with(context)
                .load(ENDPOINT_REFRESH_TOKEN)
                .setBodyParameter("client_id", this.clientId)
                .setBodyParameter("client_secret", this.clientSecret)
                .setBodyParameter("grant_type", "refresh_token")
                .setBodyParameter("refresh_token", this.token.getRefreshToken())
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        int code = result.getHeaders().code();
                        if (e != null) {
                            // We will end up here on e.g. network errors (UnknownHostException)
                            callback.onError(code, "Something went wrong");
                        } else {
                            if (result.getHeaders().code() == 200) {
                                // In this hello world example we only use the access token.
                                String accessToken = result.getResult().get("access_token").getAsString();
                                String refreshToken = result.getResult().get("refresh_token").getAsString();
                                Token token = new Token(accessToken, refreshToken);
                                callback.onSuccess(token);
                            } else {
                                String msg = "Request failed, got a " + result.getHeaders().code();
                                callback.onError(code, msg);
                            }
                        }
                    }
                });
    }

    private static final String ENDPOINT_GET_ACCESS_TOKEN = "https://platform.lifelog.sonymobile.com/oauth/2/token";
    private static final String ENDPOINT_REFRESH_TOKEN    = "https://platform.lifelog.sonymobile.com/oauth/2/refresh_token";
    private static final String ENDPOINT_GET_PROFILE      = "https://platform.lifelog.sonymobile.com/v1/users/me";
    private static final String ENDPOINT_GET_ACTIVITY     = "https://platform.lifelog.sonymobile.com/v1/users/me/activities";
    private static final String ENDPOINT_GET_LOCATION     = "https://platform.lifelog.sonymobile.com/v1/users/me/locations";

    private Context context;
    private String clientId;
    private String clientSecret;
    private Token token;
    private String scope;
}
