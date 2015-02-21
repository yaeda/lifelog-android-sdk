Lifelog Android SDK
=========================

Androidよくわかんないけど，こんなかんじ？

```java
final Lifelog lifelog = Lifelog.getInstance(this);
lifelog.getAccessToken(code, new Lifelog.TokenCallback() {
  @Override
  publib void onSuccess(Token token) {
    lifelog.setToken(token);
  }

  @Override
  public void onError(int code, String message) {
  }
});
```

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
