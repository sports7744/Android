package com.cookandroid.megagenie2;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://172.16.100.233/login.php";
    private Map map;

    public LoginRequest(String id, String password, Response.Listener listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
        map.put("password", password);
    }

    @Override
    protected  Map getParams() throws AuthFailureError{
        return map;
    }
}
