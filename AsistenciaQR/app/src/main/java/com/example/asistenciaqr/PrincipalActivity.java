package com.example.asistenciaqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEditar,btnDesconectarse,btnTomarAsistencia;
    TextView lblUsuario,lblNombre,lblEmail;

    int id=0;
    Usuario usuario;
    daoUsuario dao;

    String TAG="GenerateQrCode";
    ImageView qrImg;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        lblUsuario=findViewById(R.id.txtPrincipalUsuario);
        lblNombre=findViewById(R.id.txtPrincipalNombreC);
        lblEmail=findViewById(R.id.txtREmail);

        btnEditar=findViewById(R.id.btnEditarCuenta);
        btnDesconectarse=findViewById(R.id.btnDesconectarse);
        btnTomarAsistencia=findViewById(R.id.btnTomarAsistencia);

        btnEditar.setOnClickListener(this);
        btnDesconectarse.setOnClickListener(this);
        btnTomarAsistencia.setOnClickListener(this);

        Bundle b=getIntent().getExtras();
        id=b.getInt("Id");
        dao=new daoUsuario(this);
        usuario=dao.getUsuarioById(id);

        lblUsuario.setText(usuario.getUsuario());
        lblNombre.setText(usuario.getNombres()+" "+usuario.getApellidos());
        lblEmail.setText(usuario.getEmail());

        qrImg=findViewById(R.id.imgQR);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTomarAsistencia:
                break;
            case R.id.btnDesconectarse:
                Intent intent=new Intent(PrincipalActivity.this,LoginActivity.class);
                PrincipalActivity.this.startActivity(intent);
                finish();
                break;
        }
    }
}