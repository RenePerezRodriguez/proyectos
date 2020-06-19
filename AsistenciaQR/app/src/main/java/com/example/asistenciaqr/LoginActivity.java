package com.example.asistenciaqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText usuario,password;
    Button btnLogin,bntRegistrarse;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario=findViewById(R.id.txtRUsuario);
        password=findViewById(R.id.txtRPassword);

        btnLogin=findViewById(R.id.btnLogin);
        bntRegistrarse=findViewById(R.id.bntRegistrarse);

        btnLogin.setOnClickListener(this);
        bntRegistrarse.setOnClickListener(this);
        dao=new daoUsuario(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                String u=usuario.getText().toString();
                String p=password.getText().toString();
                if (u.equals("")&&p.equals("")){
                    Toast.makeText(this,"ERROR: Campos vacios",Toast.LENGTH_LONG).show();
                }else if (dao.login(u,p)==1){
                    Usuario us=dao.getUsuario(u,p);
                    Toast.makeText(this,"Datos correctos",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, PrincipalActivity.class);
                    i.putExtra("Id",us.getId());
                    LoginActivity.this.startActivity(i);
                }
            break;

            case R.id.bntRegistrarse:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            break;
        }
    }
}