package com.cookandroid.megagenie2;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;
import java.util.HashMap;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://172.16.100.233/register.php";
    private Map parameters;

    public RegisterRequest(String id, String password, String name, String email, Response.Listener<String>listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("password", password);
        parameters.put("name", name);
        parameters.put("email", email);
    }

    @Override
    protected Map getParams() {
        return parameters;
    }
}
