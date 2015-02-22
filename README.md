Lifelog Android SDK
=========================

Andorid SDK of Lifelog API



## Examples
Androidよくわかんないけど，こんなかんじ？

### Authentication
```java
/* create instance */
final Lifelog lifelog = Lifelog.getInstance(this);
lifelog.setCredential(CLIENT_ID, CLIENT_SECRET);
lifelog.setScope(Lifelog.Scope.READ_PROFILE    |
                 Lifelog.Scope.READ_ACTIVITIES |
                 Lifelog.Scope.READ_LOCATIONS);

/* call api to get access token */
lifelog.getAccessToken(code, new Lifelog.TokenCallback() {
  @Override
  publib void onSuccess(Lifelog.Token token) {
    /* set token */
    lifelog.setToken(token);
  }

  @Override
  public void onError(int code, String message) {
  }
});
```

### Call API
```java
lifelog.getActivities(new Lifelog.LogCallback<List<Lifelog.Activity>>() {
  @Override
  publib void onSuccess(<List<Lifelog.Activity>> activities) {
  }

  @Override
  public void onError(int code, String message) {
  }
});
```

### Call API with query parameter
```java
/* time object */
Date now = new Date();
Date eightHourBefore = new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(8));

/* build query */
Lifelog.Query query = new Lifelog.Query();
query.setStartTime(eightHourBefore);
query.setEndTime(now);
query.setType(Lifelog.Type.PHYSICAL);
query.setLimit(100);

/* call api */
lifelog.getActivities(query, new Lifelog.LogCallback<List<Lifelog.Activity>>() {
  @Override
  publib void onSuccess(<List<Lifelog.Activity>> activities) {
  }

  @Override
  public void onError(int code, String message) {
  }
});
```


## Definitions

### Lifelog.Scope
- READ_PROFILE
- READ_ACTIVITIES
- READ_LOCATIONS

### Lifelog.Type
- PHYSICAL
- APPLICATION
- CAMERA
- MUSIC
- PHYSICAL
- SLEEP
- TRANSPORT

### Lifelog.SubType
- BOOKS
- BROWSING
- CAMERA_ALBUM
- COMMUNICATION
- GAME
- MOVIE_TV
- WALK
- RUN
- BICYCLE
- OTHER


## Classes / Interfaces

### Lifelog
- `public static getInstance(Context context)`
- `public void setCredential(String clientId, String clientSecret)`
- `public void setScope(Lifelog.Scope scope)`
- `public void setToken(Lifelog.Token token)`
- `public void cancel()`
- `public void getAccessToken(String code, Lifelog.TokenCallback callback)`
- `public void refreshToken(Lifelog.TokenCallback callback)`
- `public void getProfile(Lifelog.LogCallback<List<Lifelog.Profile>> callback)`
- `public void getActivities(Lifelog.LogCallback<List<Lifelog.Activity>> callback)`
- `public void getActivities(Lifelog.Query query, Lifelog.LogCallback<List<Lifelog.Activity>> callback)`
- `public void getLocations(Lifelog.LogCallback<List<Lifelog.Location> callback>)`
- `public void getLocations(Lifelog.Query query, Lifelog.LogCallback<List<Lifelog.Location> callback>)`

### Lifelog.Query
- `public void setStartTime(Date date)`
- `public void setEndTime(Date date)`
- `public void setType(Lifelog.Type type)`
- `public void setLimit(int limit)`

### Lifelog.Token
- `public void setAccessToken(String accessToken)`
- `public void setRefreshToken(String refreshToken)`
- `public String getAccessToken()`
- `public String getRefreshToken()`

### Lifelog.Profile

### Lifelog.Activity

### Lifelog.Location

### Lifelog.TokenCallback
- `public void onSuccess(Lifelog.Token token)`
- `public void onError(int code, String message)`

### Lifelog.LogCallback<List<T>>
- `public void onSuccess(List<T> logs)`
- `public void onError(int code, String message)`
