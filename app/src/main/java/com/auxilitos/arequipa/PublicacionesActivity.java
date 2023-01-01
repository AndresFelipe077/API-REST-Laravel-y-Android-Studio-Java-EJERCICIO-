package com.auxilitos.arequipa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.auxilitos.arequipa.Config.Config;
import com.auxilitos.arequipa.Interface.ApiService;
import com.auxilitos.arequipa.Model.Publicaciones;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicacionesActivity extends AppCompatActivity {

    private EditText n,d;
    private Button btnSave;
    private ListView lv;
    private AdapterPublicaciones adapter;
    private ApiService servicio= Config.getRetrofit().create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        lv = findViewById(R.id.lista);
        n = findViewById(R.id.nombre);
        d = findViewById(R.id.descripcion);
        btnSave = findViewById(R.id.btnSave);


        getPublicaciones();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(n.getText().toString().isEmpty())
                    Config.Mensaje(getApplicationContext(),"INGRESE NOMBRE");
                else if(d.getText().toString().isEmpty())
                    Config.Mensaje(getApplicationContext(),"INGRESE DESCRIPCIÓN");
                else{
                    savePublicacion(
                            n.getText().toString(),
                            d.getText().toString()
                    );
                }

            }
        });
    }//fin onCreate


    private void savePublicacion(String n,String d){
        Call<Publicaciones> call = servicio.savePublicacion(n,d);
        call.enqueue(new Callback<Publicaciones>() {
            @Override
            public void onResponse(Call<Publicaciones> call, Response<Publicaciones> response) {
                if(response.isSuccessful()) {
                    Config.Mensaje(getApplicationContext(), "ok");
                    startActivity(new Intent(getApplicationContext(),PublicacionesActivity.class));
                    finish();
                }
                else
                    Config.Mensaje(getApplicationContext(),"error");
            }
            @Override
            public void onFailure(Call<Publicaciones> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });
    }


    private void getPublicaciones(){
        Call<List<Publicaciones>> listCall = servicio.getPublicaciones();
        listCall.enqueue(new Callback<List<Publicaciones>>() {
            @Override
            public void onResponse(Call<List<Publicaciones>> call, Response<List<Publicaciones>> response) {
                if(response.isSuccessful()){
                    Log.d("ERROR",response.body().toString());
                    adapter = new AdapterPublicaciones(getApplicationContext(),response.body());
                    lv.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Publicaciones>> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });
    }


    class AdapterPublicaciones extends ArrayAdapter<Publicaciones> {
        List<Publicaciones> listapublicacion;
        public AdapterPublicaciones(Context context, List<Publicaciones> lista) {
            super(context, R.layout.lista, lista);
            listapublicacion = lista;
        }
        @Override
        public View getView(int position,  View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.lista,null);
            TextView n= view.findViewById(R.id.txtnombre);
            TextView d= view.findViewById(R.id.txtdescripcionn);
            n.setText(listapublicacion.get(position).getNombre());
            d.setText(listapublicacion.get(position).getDescripcion());
            return view;
        }
    }

}//Fin todo

