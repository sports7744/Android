package com.cookandroid.megagenie2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PwCheckActivity extends AppCompatActivity {

    private EditText Id, Name, Email;
    private Button btnPwCheck, pwLogin;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pwcheck);

        Id = findViewById(R.id.pwCheck_id);
        Name = findViewById(R.id.pwCheck_name);
        Email = findViewById(R.id.pwCheck_email);
        btnPwCheck = findViewById(R.id.btnPwCheck);
        pwLogin = findViewById(R.id.pwLogin);

        pwLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PwCheckActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        btnPwCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String id = Id.getText().toString();
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                if(validate) {
                    return;
                }
                if(id.equals("") || name.equals("") || email.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder( PwCheckActivity.this );
                    dialog = builder.setMessage("입력사항을 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String password = jsonObject.getString("password");
                                validate = true;
                                AlertDialog.Builder builder = new AlertDialog.Builder( PwCheckActivity.this );
                                dialog = builder.setMessage("당신의 비밀번호는 " + password + "입니다!")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder( PwCheckActivity.this );
                                dialog = builder.setMessage("입력하신 정보가 존재하지 않습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                PwCheckRequest pwcheckRequest = new PwCheckRequest(id, name, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PwCheckActivity.this);
                queue.add(pwcheckRequest);
            }
        });
    }
}
