package com.example.asistenciaqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText rUsuario, rPassword, rEmail, rNombres, rApellidos;
    Button Bregistrarse, back;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       rUsuario =findViewById(R.id.txtRUsuario);
        rPassword =findViewById(R.id.txtRPassword);

        rEmail =findViewById(R.id.txtREmail);
        rNombres =findViewById(R.id.txtRNombres);
        rApellidos =findViewById(R.id.txtRApellidos);

        Bregistrarse =findViewById(R.id.btnRegRegistrar);
        back=findViewById(R.id.btnRegBack);

        Bregistrarse.setOnClickListener(this);
        back.setOnClickListener(this);

        dao=new daoUsuario(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegRegistrar:
                Usuario usuario=new Usuario();
                usuario.setUsuario(rUsuario.getText().toString());
                usuario.setPassword(rPassword.getText().toString());
                usuario.setEmail(rEmail.getText().toString());
                usuario.setNombres(rNombres.getText().toString());
                usuario.setApellidos(rApellidos.getText().toString());
                if (!usuario.isNull()){
                    Toast.makeText(this,"ERROR: Campos Vacios",Toast.LENGTH_LONG).show();
                }else if (dao.insertUsuario(usuario)){
                    Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_LONG).show();
                    Intent intent2=new Intent(RegisterActivity.this,LoginActivity.class);
                    RegisterActivity.this.startActivity(intent2);
                    finish();
                }else {
                    Toast.makeText(this,"Usuario ya Existente",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegBack:
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                finish();
                break;
        }
    }
}