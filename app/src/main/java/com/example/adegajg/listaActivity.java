package com.example.adegajg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adegajg.modelo.Produto;
import com.example.adegajg.webservice.Api;
import com.example.adegajg.webservice.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class listaActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextIdProduto;
    EditText editTextProduto;
    EditText editTextPreco;
    EditText editTextDistribuidor;
    EditText editTextQuantidade;
    Button buttonSalvar;
    ProgressBar progressBar;
    ListView listView;
    List<Produto> produtoList;

    Boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        progressBar = findViewById(R.id.barraProgresso);
        listView = findViewById(R.id.listViewProdutos);

        editTextIdProduto = findViewById(R.id.editTextIdProduto);
        editTextProduto = findViewById(R.id.editTextProduto);
        editTextPreco = findViewById(R.id.editTextPreco);
        editTextDistribuidor = findViewById(R.id.editTextDistribuidor);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        produtoList = new ArrayList<>();

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUpdating){
                    updateProduto();
                }
                else{
                    createProduto();
                }
            }
        });

        Button botaovoltar = findViewById(R.id.buttonvoltarlista);
        botaovoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LinkLista = new Intent(listaActivity.this, MainActivity.class);
                startActivity(LinkLista);
            }
        });

        readProduto();

    }

    private void createProduto(){
        String produto = editTextProduto.getText().toString().trim();
        String preco = editTextPreco.getText().toString().trim();
        String distribuidor = editTextDistribuidor.getText().toString().trim();
        String quantidade = editTextQuantidade.getText().toString().trim();

        if(TextUtils.isEmpty(produto)){
            editTextProduto.setError("Digite o nome produto");
            editTextProduto.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(preco)){
            editTextPreco.setError("Digite o preço do produto");
            editTextPreco.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(distribuidor)){
            editTextDistribuidor.setError("Digite o distribuidor do produto");
            editTextDistribuidor.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(quantidade)){
            editTextQuantidade.setError("Digite a quantidade do produto");
            editTextQuantidade.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("produto", produto);
        params.put("preco", preco);
        params.put("distribuidor", distribuidor);
        params.put("quantidade", quantidade);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_APPADEGA, params, CODE_POST_REQUEST);
        request.execute();

    }

    private void updateProduto(){
        String id = editTextIdProduto.getText().toString();
        String produto = editTextProduto.getText().toString().trim();
        String preco = editTextPreco.getText().toString().trim();
        String distribuidor = editTextDistribuidor.getText().toString().trim();
        String quantidade = editTextQuantidade.getText().toString().trim();

        if(TextUtils.isEmpty(produto)){
            editTextProduto.setError("Digite o nome produto");
            editTextProduto.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(preco)){
            editTextPreco.setError("Digite o preço do produto");
            editTextPreco.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(distribuidor)){
            editTextDistribuidor.setError("Digite o distribuidor do produto");
            editTextDistribuidor.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(quantidade)){
            editTextQuantidade.setError("Digite a quantidade do produto");
            editTextQuantidade.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("produto", produto);
        params.put("preco", preco);
        params.put("distribuidor", distribuidor);
        params.put("quantidade", quantidade);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_APPADEGA, params, CODE_POST_REQUEST);
        request.execute();

        buttonSalvar.setText("Salvar");
        editTextProduto.setText("");
        editTextPreco.setText("");
        editTextDistribuidor.setText("");
        editTextQuantidade.setText("");

        isUpdating = false;


    }

    private void readProduto() {
        PerformNetworkRequest request =  new PerformNetworkRequest(Api.URL_READ_APPADEGA, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void deleteProduto(int id){
        PerformNetworkRequest request =  new PerformNetworkRequest(Api.URL_DELETE_APPADEGA+id, null, CODE_GET_REQUEST);
        request.execute();
    }



    private void refreshProdutoList(JSONArray produto) throws JSONException{
        produtoList.clear();

        for (int i = 0; i < produto.length(); i++){
            JSONObject obj = produto.getJSONObject(i);
            produtoList.add(new Produto(
                    obj.getInt("id"),
                    obj.getString("produto"),
                    obj.getString("preco"),
                    obj.getString("distribuidor"),
                    obj.getString("quantidade")
            ));
        }
        ProdutoAdapter adapter = new ProdutoAdapter(produtoList);
        listView.setAdapter(adapter);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"),Toast.LENGTH_SHORT).show();
                    refreshProdutoList(object.getJSONArray("appadega"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }


    }

    class ProdutoAdapter extends ArrayAdapter<Produto>{

        List<Produto> produtoList;

        public ProdutoAdapter(List<Produto> produtoList){
            super(listaActivity.this, R.layout.layout_adega_list, produtoList);

            this.produtoList = produtoList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_adega_list, null, true);

            TextView textViewServico = listViewItem.findViewById(R.id.textViewProduto);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);
            TextView textViewAlterar = listViewItem.findViewById(R.id.textViewAlterar);

            final Produto produto = produtoList.get(position);

            textViewServico.setText(produto.getProduto());
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(listaActivity.this);

                    builder.setTitle("Delete " + produto.getProduto())
                            .setMessage("Você quer realmente deletar?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteProduto(produto.getId());

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            textViewAlterar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isUpdating = true;
                    editTextIdProduto.setText(String.valueOf(produto.getId()));
                    editTextProduto.setText(String.valueOf(produto.getProduto()));
                    editTextPreco.setText(String.valueOf(produto.getPreco()));
                    editTextDistribuidor.setText(String.valueOf(produto.getDistribuidor()));
                    editTextQuantidade.setText(String.valueOf(produto.getQuantidade()));
                    buttonSalvar.setText("Alterar");
                }
            });
            return listViewItem;

        }
    }


}
