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
Date eightHourBefore = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 8);

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
