package br.com.lucaspaivac.rumoaotop500.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import br.com.lucaspaivac.rumoaotop500.R;
import br.com.lucaspaivac.rumoaotop500.util.ToolBox;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private Button btn_top500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVariaveis();
        inicializarAcoes();

    }

    private void inicializarVariaveis() {
        context = MainActivity.this;

        btn_top500 = findViewById(R.id.btn_top500);
    }

    private void inicializarAcoes() {
        btn_top500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolBox.nav(context, FormActivity.class);
            }
        });
    }
}
