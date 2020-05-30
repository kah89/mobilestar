package com.meuapp.starpasseios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private String apiPath = "http://10.0.2.2/ProjetoStar/usuarios/lista/";
    private ProgressDialog progressDialog;
    private JSONArray restulJsonArray;
    private ProgressBar progressBar = null;
    private int result = 0;
    Context context;
    private int success = 0;


    EditText edtUsuario, edtSenha;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);

        context = getApplicationContext();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsuario, strSenha;
               // String varusuario, varsenha;

                int codigousuario,logado;

                codigousuario = 0;
                logado = 0;

                // int codigousuario =0;
                //int logado =0;

              //  varusuario = "karina";
                // varsenha = "1234";

                strUsuario = edtUsuario.getText().toString();
                strSenha = edtSenha.getText().toString();

                //if (varusuario.equals(strUsuario) && varsenha.equals(strSenha)) {

                  UsuariosTable usuariosTable = new UsuariosTable(codigousuario,strUsuario,strSenha,logado);

                  new VerificarUsuariosAsyncTask().execute(usuariosTable);



               // } else {
                 /*   AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this).
                 //           setTitle("Erro").
                            setMessage("Usuário ou senha incorretos").
                            setPositiveButton("OK", null);

                    builder.create().show();


                }*/

            }
        });

    }

    protected class VerificarUsuariosAsyncTask extends AsyncTask<UsuariosTable, Void, JSONObject> {

        String response = "";
        HashMap<String, String> postParametros;

        ArrayList<UsuariosTable> usuariostable = null;


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected JSONObject doInBackground(UsuariosTable... params) {
            usuariostable = new ArrayList<UsuariosTable>();

            postParametros = new HashMap<String, String>();
            postParametros.put("HTTP_ACCEPT", "application/json");
            postParametros.put("txtUsuario", params[0].getNomeUsuario());
            postParametros.put("txtSenha", params[0].getSenhaUsuario());


            HttpConnectionService service = new HttpConnectionService();
            response = service.sendRequest(apiPath, postParametros);


            try {

                JSONObject resultJsonObject = new JSONObject(response);
                result = resultJsonObject.getInt("sucess");
            } catch (JSONException e) {
                success = 0;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            super.onPostExecute(response);
            progressBar.setVisibility(View.INVISIBLE);

            if (result == 0 ) {

                new AlertDialog.Builder(LoginActivity.this).setTitle("Aviso")
                        .setMessage("Bem-vindo ao Sistema")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent BaseActivity = new Intent(LoginActivity.this, BaseActivity.class);
                                startActivity(BaseActivity);
                                finish();
                            }
                        })
                        .show();

                Toast.makeText(context, "Não foi possível inserir o Produto", Toast.LENGTH_SHORT).show();
            } /*else {
                Toast.makeText(context, "Produto inserido com sucesso", Toast.LENGTH_SHORT).show();
            }*/


            edtUsuario.setText ("");
            edtSenha.setText ("");
            edtUsuario.setText ("");

        }
    }
}


