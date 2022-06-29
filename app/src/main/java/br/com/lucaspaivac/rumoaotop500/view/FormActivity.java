package br.com.lucaspaivac.rumoaotop500.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import br.com.lucaspaivac.rumoaotop500.R;
import br.com.lucaspaivac.rumoaotop500.bd.SqlHelper;
import br.com.lucaspaivac.rumoaotop500.model.Register;
import br.com.lucaspaivac.rumoaotop500.util.Constantes;
import br.com.lucaspaivac.rumoaotop500.util.ToolBox;

public class FormActivity extends AppCompatActivity {

    private Context context;

    private Spinner sp_role;
    private Spinner sp_vd;

    private EditText et_sr;

    private Button btn_save;

    private int lastSR;

    private int calcPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        inicializarVariaveis();
        inicializarAcoes();

    }

    private void inicializarVariaveis() {

        context = FormActivity.this;

        sp_role = findViewById(R.id.sp_role);
        sp_vd = findViewById(R.id.sp_vd);
        et_sr = findViewById(R.id.et_sr);
        btn_save = findViewById(R.id.btn_save);
    }

    private void inicializarAcoes() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ToolBox.validate(et_sr) == false){
                    ToolBox.toast(context, R.string.invalid_sr);
                    return;
                }

                final String roleSelected = sp_role.getSelectedItem().toString();
                final String resPartida = ToolBox.resPartidaResponse(sp_vd);
                final int sr = ToolBox.convertValueI(et_sr.getText().toString());

                SqlHelper helper = SqlHelper.getInstance(context);
                List<Register> list = helper.getRegisterBy(roleSelected);
                if (list.isEmpty() || list.size() == 0){
                    calcPoints = 0;
                }else {
                    Register register = list.get(list.size() -1);
                    lastSR = register.getSr();
                    if (ToolBox.validateTwo(resPartida, lastSR, sr) == false){
                        if (resPartida == "Vitória"){
                            ToolBox.alertDialogCreator(context, R.string.invalid_data,
                                    "Vitória com SR menor ou igual ao último registrado.\n" +
                                            getString(R.string.last_sr_message, lastSR) + "\n" +
                                            getString(R.string.sr_writed_message, sr), R.drawable.baseline_report_problem_black_24dp,
                                    "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface,
                                                            int i) {
                                            et_sr.requestFocus();
                                            dialogInterface.dismiss();
                                        }
                                    });
                            return;
                        }
                        if (resPartida == "Derrota"){
                            ToolBox.alertDialogCreator(context, R.string.invalid_data,
                                    "Derrota com SR maior ou igual ao último registrado.\n" +
                                            getString(R.string.last_sr_message, lastSR) + "\n" +
                                            getString(R.string.sr_writed_message, sr), R.drawable.baseline_report_problem_black_24dp,
                                    "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface,
                                                            int i) {
                                            et_sr.requestFocus();
                                            dialogInterface.dismiss();
                                        }
                                    });
                            return;
                        }
                        if (resPartida == "Empate"){
                            ToolBox.alertDialogCreator(context, R.string.invalid_data,
                                    "Empate com SR diferente ao último registrado.\n" +
                                            getString(R.string.last_sr_message, lastSR) + "\n" +
                                            getString(R.string.sr_writed_message, sr), R.drawable.baseline_report_problem_black_24dp,
                                    "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface,
                                                            int i) {
                                            et_sr.requestFocus();
                                            dialogInterface.dismiss();
                                        }
                                    });
                            return;
                        }
                    }
                    calcPoints = sr - lastSR;
                }

                ToolBox.alertDialogCreator(context, ToolBox.titlereponse(sp_vd), getString(ToolBox.messageResponse(sp_vd), calcPoints), ToolBox.iconResponse(sp_vd),
                        "Salvar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SqlHelper helper = SqlHelper.getInstance(context);
                                long regId = helper.addItem(roleSelected, resPartida, sr, calcPoints);

                                if (regId > 0){
                                    ToolBox.toast(context, getString(R.string.register_saved));
                                    ToolBox.openListActivity(context, roleSelected);
                                }

                            }
                        }, "Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.it_menu: ToolBox.openListActivity(context, sp_role.getSelectedItem().toString());
            default: return super.onOptionsItemSelected(item);
        }
    }

}
