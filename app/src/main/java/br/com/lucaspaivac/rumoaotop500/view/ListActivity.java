package br.com.lucaspaivac.rumoaotop500.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import br.com.lucaspaivac.rumoaotop500.R;
import br.com.lucaspaivac.rumoaotop500.bd.SqlHelper;
import br.com.lucaspaivac.rumoaotop500.model.Register;
import br.com.lucaspaivac.rumoaotop500.util.Constantes;
import br.com.lucaspaivac.rumoaotop500.util.ToolBox;

public class ListActivity extends AppCompatActivity {

    private Context context;

    private RecyclerView rv_list;
    private List<Register> registers = new ArrayList<>();
    private ListAdapter adapter;

    private RadioGroup radioGroup;
    private RadioButton radioButtonDPS;
    private RadioButton radioButtonTank;
    private RadioButton radioButtonSup;

    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        inicializarVariaveis();
        inicializarAcoes();

    }

    private void inicializarVariaveis() {
        context = ListActivity.this;
        rv_list = findViewById(R.id.rv_list);
        radioGroup = findViewById(R.id.rg_roles);
        radioButtonDPS = findViewById(R.id.list_rb_dps);
        radioButtonTank = findViewById(R.id.list_rb_tank);
        radioButtonSup = findViewById(R.id.list_rb_sup);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            role = extras.getString(Constantes.ROLE);

            initialRbSelected(role);

            SqlHelper helper = SqlHelper.getInstance(context);
            registers.addAll(helper.getRegisterBy(role));

            adapter = new ListAdapter(context, registers);
            rv_list.setAdapter(adapter);
            rv_list.setLayoutManager(new LinearLayoutManager(context));
        }

    }

    private void inicializarAcoes() {
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menulist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.it_menu_list: ToolBox.limparBancoRole(context, role); buscarLista(role);
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void initialRbSelected(String role){
        switch (role){
            case "DPS": radioButtonDPS.setChecked(true); radioButtonTank.setChecked(false); radioButtonSup.setChecked(false);
            break;
            case "Tank": radioButtonDPS.setChecked(false); radioButtonTank.setChecked(true); radioButtonSup.setChecked(false);
            break;
            case "Sup": radioButtonDPS.setChecked(false); radioButtonTank.setChecked(false); radioButtonSup.setChecked(true);
            break;
            default: radioButtonDPS.setChecked(false); radioButtonTank.setChecked(false); radioButtonSup.setChecked(false);
        }
    }

    public void checkButton (View v){
        int radioId = radioGroup.getCheckedRadioButtonId();

        switch (radioId){
            case R.id.list_rb_dps: buscarLista("DPS"); role = "DPS";
            break;
            case R.id.list_rb_tank: buscarLista("Tank"); role = "Tank";
            break;
            case R.id.list_rb_sup: buscarLista("Sup"); role = "Sup";
            break;
            default: ToolBox.toast(context, "Erro CheckButton");
        }
    }

    public void buscarLista(String role){
        SqlHelper helper = SqlHelper.getInstance(context);
        adapter = new ListAdapter(context, helper.getRegisterBy(role));
        rv_list.setAdapter(adapter);
        rv_list.setLayoutManager(new LinearLayoutManager(context));
    }

}
