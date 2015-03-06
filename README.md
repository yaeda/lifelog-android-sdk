Lifelog Android SDK
=========================

Andorid SDK of Lifelog API

[Lifelog API | Sony Developer World](https://developer.sony.com/develop/services/lifelog-api/)

## Examples
Android/Javaよくわかんないけど，こんなかんじ？

### Authentication
```java
/* create instance */
final Lifelog lifelog = new Lifelog(this);
lifelog.setCredential(CLIENT_ID, CLIENT_SECRET);
lifelog.setScope(Lifelog.Scope.READ_PROFILE    |
                 Lifelog.Scope.READ_ACTIVITIES |
                 Lifelog.Scope.READ_LOCATIONS);

/* call api to get access token */
lifelog.getAccessToken(code, new TokenCallback() {
  @Override
  publib void onSuccess(Token token) {
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
lifelog.getActivities(new LogCallback<Activity>() {
  @Override
  publib void onSuccess(List<Activity> activities) {
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
Lifelog.Query query = new Query();
query.setStartTime(eightHourBefore);
query.setEndTime(now);
query.setType(Lifelog.Type.PHYSICAL);
query.setLimit(100);

/* call api */
lifelog.getActivities(query, new LogCallback<Activity>() {
  @Override
  publib void onSuccess(List<Activity> activities) {
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
- `public Lifelog(Context context)`
- `public void setCredential(String clientId, String clientSecret)`
- `public void setScope(int scope)`
- `public void setToken(Token token)`
- `public void cancel()`
- `public void getAccessToken(String code, TokenCallback callback)`
- `public void refreshToken(TokenCallback callback)`
- `public void getProfile(LogCallback<Profile> callback)`
- `public void getActivities(LogCallback<Activity> callback)`
- `public void getActivities(Query query, LogCallback<Activity> callback)`
- `public void getLocations(LogCallback<Location> callback)`
- `public void getLocations(Query query, LogCallback<Location> callback)`

### Query
- `public void setStartTime(Date date)`
- `public void setEndTime(Date date)`
- `public void setType(Lifelog.Type type)`
- `public void setLimit(int limit)`

### Token
- `public void setAccessToken(String accessToken)`
- `public void setRefreshToken(String refreshToken)`
- `public String getAccessToken()`
- `public String getRefreshToken()`

### Profile

### Activity

### Location

### TokenCallback
- `public void onSuccess(Token token)`
- `public void onError(int code, String message)`

### LogCallback&lt;T&gt;
- `public void onSuccess(List<T> logs)`
- `public void onError(int code, String message)`
