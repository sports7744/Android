package com.cookandroid.megagenie2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText join_id, join_pwd, join_name, join_email;
    private Button Join, overlap_check;
    private boolean validate = false;
    private boolean validate2 = false;
    private boolean idcheck = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        join_id = findViewById(R.id.join_id);
        join_pwd = findViewById(R.id.join_pwd);
        join_name = findViewById(R.id.join_name);
        join_email = findViewById(R.id.join_email);

        Join = findViewById(R.id.Join);
        overlap_check = findViewById(R.id.overlap_check);

        overlap_check.setOnClickListener(new View.OnClickListener() {//id중복체크
            @Override
            public void onClick(View view) {
                String id = join_id.getText().toString();
                if(validate)
                {
                    return;
                }
                if(id.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder( RegisterActivity.this );
                    dialog = builder.setMessage("아이디를 써주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();

                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder( RegisterActivity.this );
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                join_id.setEnabled(false);
                                validate=true;
                                idcheck=true;
                                overlap_check.setText("확인");
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                                idcheck=false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest( id, responseListener);
                RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);

            }
        });

        Join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                String id = join_id.getText().toString();
                String password = join_pwd.getText().toString();
                String name = join_name.getText().toString();
                String email = join_email.getText().toString();
                if(validate2)
                {
                    return;
                }
                if(id.equals("") || password.equals("") || name.equals("") || email.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder( RegisterActivity.this );
                    dialog = builder.setMessage("모두 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success && idcheck == true) {//회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                validate2=true;
                            } else { //회원 등록에 실패한 경우
                                if (idcheck == false) {
                                    Toast.makeText(getApplicationContext(), "아이디 중복!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입 실패!", Toast.LENGTH_LONG).show();
                                }
                                return;
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 Volley를 이용해서 요청함.
                RegisterRequest registerRequest = new RegisterRequest(id, password, name, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }

}

