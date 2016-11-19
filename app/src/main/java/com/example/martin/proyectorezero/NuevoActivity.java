package com.example.martin.proyectorezero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NuevoActivity extends Activity {

    TextView lbl1;
    EditText edt1, edt2;
    Button btn1, btn2;

    String codigo = "";
    String mensaje = "";

    ArrayList estado = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_2);

        lbl1 = (TextView) findViewById(R.id.lblEditable);

        edt1 = (EditText) findViewById(R.id.editText);
        edt2 = (EditText) findViewById(R.id.editText2);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        Intent intentrecibido = getIntent();

        String mensajeResivido = intentrecibido.getStringExtra("TipoAccion");


        switch (mensajeResivido) {

            case "modificar":

                lbl1.setText("ESTA PANTALLA SERA PARA MODIFICAR");

                edt1.setHint(intentrecibido.getStringExtra("Codigo") + "-> :V");
                edt2.setText(" ");
                edt2.setFocusable(true);

                codigo = intentrecibido.getStringExtra("Codigo");

                mensaje = "";

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mensaje = edt2.getText().toString();

                        metodoModificar(codigo, mensaje);

                    }
                });


                break;

            case "agregar":

                lbl1.setText("ESTA PANTALLA ES PARA GUARDAR");
                edt1.setHint("Campo Codigo Autogenerado....");
                edt2.setFocusable(true);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        metodo1();

                    }
                });

                break;

            default:

                Toast.makeText(getApplicationContext(), "NO LLEGO PARAMETRO...", Toast.LENGTH_SHORT).show();

        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Salir();

            }
        });


    }


    public void Salir() {

        Intent intent = new Intent(NuevoActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public void metodo1() {
        Toast.makeText(getApplicationContext(), "HOLA GUARDAR", Toast.LENGTH_SHORT).show();
    }

    public void metodoModificar(String codigo, String contenidocampo) {

        estado.clear();

        Toast.makeText(getApplicationContext(), "LOS DATOS SON :" + codigo + " y " + contenidocampo, Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "http://examenfinal2016.esy.es/PROYECTOWEBSERVICE/CONTROLADOR/SexoControlador.php?op=4";

        RequestParams params = new RequestParams();
        params.add("codsexo", codigo);
        params.add("nombsex", contenidocampo);

        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {

                    try {

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i = 0; i < jsonArray.length(); i++) {

                            estado.add(jsonArray.getJSONObject(i).getString("estado"));

                        }

                        if (estado.get(0).toString() == "success") {

                            Toast.makeText(getApplicationContext(), " SE ACTUALIZO CORRECTAMENTE ", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getApplicationContext(), " NO SE ACTUALIZO :V", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception ex) {

                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(), " OCURRIO ALGO EN EL PROCESO... ", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
