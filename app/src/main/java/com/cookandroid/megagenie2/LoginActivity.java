package com.cookandroid.megagenie2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private EditText ID, Password;
    private Button LogIn, Join, Find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ID = findViewById(R.id.ID);
        Password = findViewById(R.id.Password);
        LogIn = findViewById(R.id.LogIn);
        Join = findViewById(R.id.Join);
        Find = findViewById(R.id.Find);

        Join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, IdCheckActivity.class);
                startActivity(intent);
            }
        });

        LogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String id = ID.getText().toString();
                String password = Password.getText().toString();

                Response.Listener responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ // 응답을 성공적으로 받았을 때 onResponse 객체를 자동으로 호출
                        try{
                            System.out.println("MegaGenie" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){ //로그인에 성공한 경우
                                String id = jsonObject.getString("id");
                                String password = jsonObject.getString("password");

                                Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("password", password);
                                startActivity(intent);
                            } else{ //로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"아이디/비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                                return;
                            }

                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(id, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }
}
