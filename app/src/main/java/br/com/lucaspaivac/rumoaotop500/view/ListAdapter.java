package br.com.lucaspaivac.rumoaotop500.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.lucaspaivac.rumoaotop500.R;
import br.com.lucaspaivac.rumoaotop500.model.Register;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private Context context;
    private List<Register> list;

    public ListAdapter(Context context, List<Register> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_registers, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Register register = this.list.get(position);
        holder.bind(register);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
