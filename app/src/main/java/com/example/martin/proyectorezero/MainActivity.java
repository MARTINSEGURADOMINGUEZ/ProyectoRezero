package com.example.martin.proyectorezero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {

    String tipo = "";

    ArrayList codigo = new ArrayList();
    ArrayList nombre = new ArrayList();
    ArrayList combinacion = new ArrayList();

    //Este es el nuevo ejemplo
    ExpandableListView expandableListView;

    Button btn, btn1, btn2;
    EditText edt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //AQUI ENLAZAMOS TODA ACTIVIDAD CON EL .XML ESCOGIDO

        setContentView(R.layout.activity_main);

        expandableListView = (ExpandableListView)findViewById(R.id.LstvwExpan);

        btn = (Button) findViewById(R.id.button);
        btn1 = (Button) findViewById(R.id.btnBuscar);
        btn2 = (Button) findViewById(R.id.button2);

        edt1 = (EditText) findViewById(R.id.edt1);

        //AQUI CARGAMOS LOS DATOS EN EL EXPANDABLELISTVIEW
        CargarLista();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String validar = edt1.getText().toString();

                if (validar.isEmpty()) {

                    CargarLista();
                    edt1.setError("Ingrese Nonbre xfavor...");

                } else {

                    Buscar(validar);


                }

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AgregarNuevo();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CargarLista();
                Toast.makeText(getApplicationContext(), "Actualizando Registros.... :V", Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void Buscar(String nombreRecibido) {

        codigo.clear();
        nombre.clear();
        combinacion.clear();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://examenfinal2016.esy.es/PROYECTOWEBSERVICE/CONTROLADOR/SexoControlador.php?op=5";

        RequestParams requestParams = new RequestParams();
        requestParams.add("nombsex", nombreRecibido);

        //Toast.makeText(getApplicationContext(),"->"+nombre+"<-",Toast.LENGTH_SHORT).show();

        RequestHandle post = client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {

                    try {

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i = 0; i < jsonArray.length(); i++) {

                            codigo.add(jsonArray.getJSONObject(i).getInt("CODSEXO"));
                            nombre.add(jsonArray.getJSONObject(i).getString("NOMBSEXO"));

                            //Este array se creo para concatenar los array's cargados , sirve para el BaseAdapter.
                            combinacion.add(codigo + " " + nombre);
                        }

                        if (combinacion.size() == 0) {

                            combinacion.clear();
                            expandableListView.setAdapter(new MiAdaptador(getApplicationContext(), combinacion.size()));
                            Toast.makeText(getApplicationContext(), "No hay resultados...", Toast.LENGTH_SHORT).show();

                        } else {

                            expandableListView.setAdapter(new MiAdaptador(getApplicationContext(), combinacion.size()));
                            combinacion.clear();

                        }

                    } catch (Exception ex) {
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(), "NO LLEGO EL PARAMETRO ADECUADO...", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void AgregarNuevo() {

        tipo = null;

        tipo = "agregar";

        Intent intent = new Intent(MainActivity.this, NuevoActivity.class);

        intent.putExtra("TipoAccion", tipo);

        startActivity(intent);

    }

    public void Eliminar(String codigoElim) {

        codigo.clear();
        nombre.clear();
        combinacion.clear();

        //Toast.makeText(getApplicationContext(),"->"+codigoElim+"<-",Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();

        String url ="http://examenfinal2016.esy.es/PROYECTOWEBSERVICE/CONTROLADOR/SexoControlador.php?op=3";

        RequestParams params = new RequestParams();
        params.add("codsexo", codigoElim);

        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode==200){

                    try{

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for(int i=0; i<jsonArray.length();i++) {

                            codigo.add(jsonArray.getJSONObject(i).getInt("CODSEXO"));
                            nombre.add(jsonArray.getJSONObject(i).getString("NOMBSEXO"));

                            //Este array se creo para concatenar los array's cargados , sirve para el BaseAdapter.
                            combinacion.add(codigo + " " + nombre);

                        }


                        expandableListView.setAdapter(new MiAdaptador(getApplicationContext(), combinacion.size()));


                    }catch (Exception ex){

                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    public void Modificar(String Codigo, String Nombre) {

        tipo = null;

        tipo = "modificar";

        Intent intent = new Intent(MainActivity.this, NuevoActivity.class);
        intent.putExtra("TipoAccion", tipo);
        intent.putExtra("Codigo", Codigo);
        intent.putExtra("Nombre", Nombre);

        startActivity(intent);

    }

    public void CargarLista(){

       // Toast.makeText(getApplicationContext(),"NO PASA NADA",Toast.LENGTH_SHORT).show();

        codigo.clear();
        nombre.clear();
        combinacion.clear();

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://examenfinal2016.esy.es/PROYECTOWEBSERVICE/CONTROLADOR/SexoControlador.php?op=1", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode == 200){

                    try{

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for(int i=0; i<jsonArray.length();i++){

                            codigo.add(jsonArray.getJSONObject(i).getInt("CODSEXO"));
                            nombre.add(jsonArray.getJSONObject(i).getString("NOMBSEXO"));
                            combinacion.add(codigo+" "+nombre);
                        }

                        if(combinacion.size()>0){

                            expandableListView.setAdapter(new MiAdaptador(getApplicationContext(), combinacion.size()));

                        }else{

                            Toast.makeText(getApplicationContext(),"No hay datos",Toast.LENGTH_SHORT).show();

                        }



                    }catch(Exception ex){

                    }

                }}

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(),"NO OCURRE NADA",Toast.LENGTH_SHORT).show();

            }

        });

    }

    private class MiAdaptador extends BaseExpandableListAdapter{

        Context context;
        LayoutInflater layoutInflater;
        int tamaño;
        TextView lblnombre , lblcodigo,linea;
        Button btn1 , btn2;

        public MiAdaptador(Context contextApplication, int combinacion) {

            this.context = contextApplication;
            layoutInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.tamaño = combinacion;
        }

        @Override
        public int getGroupCount() {
            return tamaño;
        }

        @Override
        public int getChildrenCount(int groupPosition) { return 1; }

        @Override
        public Object getGroup(int groupPosition) {
            return combinacion.size();
        }

        //NO SE UTILIZARA AUN....
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        //NO SE UTILIZARA AUN....
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.grilla,null);

            lblcodigo = (TextView)viewGroup.findViewById(R.id.lblcodigo);
            lblnombre = (TextView)viewGroup.findViewById(R.id.lblnombre);
            linea = (TextView)viewGroup.findViewById(R.id.linea);

            lblcodigo.setText(codigo.get(groupPosition).toString());
            lblcodigo.setTextColor(getResources().getColor(R.color.colorPrimary));

            lblnombre.setText(nombre.get(groupPosition).toString());
            lblnombre.setTextColor(getResources().getColor(R.color.colorAccent));

            return viewGroup;

        }

        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.grilla2,null);

            btn1 = (Button) viewGroup.findViewById(R.id.btn1);
            btn2 = (Button) viewGroup.findViewById(R.id.btn2);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Eliminar(codigo.get(groupPosition).toString());


                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Modificar(codigo.get(groupPosition).toString(), nombre.get(groupPosition).toString());

                }
            });

            return viewGroup;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


}
