package com.example.asistenciaqr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoUsuario {
    Context context;
    Usuario usuario;
    ArrayList<Usuario> lista;
    SQLiteDatabase sqLiteDatabase;
    String bd="BDUsuarios";
    String tabla="create table if not exists usuario(id integer primary key autoincrement," +
            " usuario text, password text, email text,nombres text, apellidos text)";

    public daoUsuario(Context context){
        this.context=context;
        sqLiteDatabase=context.openOrCreateDatabase(bd,context.MODE_PRIVATE,null);
        sqLiteDatabase.execSQL(tabla);
        usuario=new Usuario();
    }

    public boolean insertUsuario(Usuario usuario){
        if (buscar(usuario.getUsuario())==0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("usuario",usuario.getUsuario());
            contentValues.put("password",usuario.getPassword());
            contentValues.put("email",usuario.getEmail());
            contentValues.put("nombres",usuario.getNombres());
            contentValues.put("apellidos",usuario.getApellidos());
            return (sqLiteDatabase.insert("usuario",null,contentValues)>0);
        }else {
            return false;
        }
    }

    public int buscar (String u){
        int x=0;
        lista=selectUsuarios();
        for (Usuario  us:lista){
            if(us.getUsuario().equals(u)){
                x++;
            }
        }
        return x;
    }

    public ArrayList<Usuario> selectUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        lista.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from usuario", null);
        if (cursor!=null&&cursor.moveToFirst()){
            do {
                Usuario u =  new Usuario();
                u.setId(cursor.getInt(0));
                u.setUsuario(cursor.getString(1));
                u.setPassword(cursor.getString(2));
                u.setEmail(cursor.getString(3));
                u.setNombres(cursor.getString(4));
                u.setApellidos(cursor.getString(5));
                lista.add(usuario);
            }while (cursor.moveToNext());
        }
        return lista;
    }

    public int login(String usuario, String password){
        int a=0;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from usuario", null);
        if (cursor!=null&&cursor.moveToFirst()) {
            do {
               if (cursor.getString(1).equals(usuario)&&cursor.getString(2).equals(password)){
                   a++;
               }
            } while (cursor.moveToNext());
        }
        return a;
    }
    public Usuario getUsuario(String usuario,String password){
        lista=selectUsuarios();
        for (Usuario u:lista){
            if (u.getUsuario().equals(usuario)&&u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }

    public Usuario getUsuarioById(int id){
        lista=selectUsuarios();
        for (Usuario u:lista){
            if (u.getId()==id){
                return u;
            }
        }
        return null;
    }
}
