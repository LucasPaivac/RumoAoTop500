package br.com.lucaspaivac.rumoaotop500.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.lucaspaivac.rumoaotop500.R;
import br.com.lucaspaivac.rumoaotop500.model.Register;

class ListViewHolder extends RecyclerView.ViewHolder {

    private final TextView tv_resPartida;
    private final TextView tv_sr;
    private final TextView tv_earnedPoints;
    private final TextView tv_date;

    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_resPartida = itemView.findViewById(R.id.list_tv_vd);
        tv_sr = itemView.findViewById(R.id.list_tv_sr);
        tv_earnedPoints = itemView.findViewById(R.id.list_tv_points);
        tv_date = itemView.findViewById(R.id.list_tv_date);
    }

    public void bind (Register register){
        tv_resPartida.setText(String.format("%s", register.getResultado()));
        tv_sr.setText(String.format("%1$d", register.getSr()));
        tv_earnedPoints.setText(String.format("%1$d", register.getCalcPoints()));
        tv_date.setText(String.format("%s", register.getCreatedDate()));
    }
}
