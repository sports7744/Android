package com.cookandroid.megagenie2;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class PwCheckRequest extends StringRequest {

    final static private String URL = "http://172.16.100.233/pwcheck.php";
    private Map map;

    public PwCheckRequest(String id, String name, String email, Response.Listener listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("email", email);
    }

    @Override
    protected  Map getParams() throws AuthFailureError{
        return map;
    }
}
