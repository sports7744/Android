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

public class IdCheckActivity extends AppCompatActivity {

    private EditText Name, Email;
    private Button btnidcheck, pwFind;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idcheck);

        Name = findViewById(R.id.idcheck_name);
        Email = findViewById(R.id.idcheck_email);
        btnidcheck = findViewById(R.id.btnidcheck);
        pwFind = findViewById(R.id.pwFind);

        pwFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IdCheckActivity.this, PwCheckActivity.class);
                startActivity(intent);
            }
        });

        btnidcheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                if(validate) {
                    return;
                }
                if(name.equals("") || email.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder( IdCheckActivity.this );
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
                                String id = jsonObject.getString("id");
                                validate = true;
                                AlertDialog.Builder builder = new AlertDialog.Builder( IdCheckActivity.this );
                                dialog = builder.setMessage("당신의 아이디는 " + id + "입니다!")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder( IdCheckActivity.this );
                                dialog = builder.setMessage("일치하는 아이디가 없습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                IdCheckRequest idcheckRequest = new IdCheckRequest(name, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(IdCheckActivity.this);
                queue.add(idcheckRequest);
            }
        });
    }
}
