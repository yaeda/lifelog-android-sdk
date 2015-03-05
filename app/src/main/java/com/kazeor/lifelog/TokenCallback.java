package com.kazeor.lifelog;

import com.kazeor.lifelog.model.Token;

public interface TokenCallback {

    public void onSuccess(Token token);

    public void onError(int code, String message);

}
