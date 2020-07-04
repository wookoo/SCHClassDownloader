package am.ze.wookoo.SCHClassDownloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView className;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
        }
    }
    private ArrayList<ClassNameData> classNameArray;

    public RecyclerAdapter(ArrayList<ClassNameData> classNameArray) {
        this.classNameArray = classNameArray;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.classinfo, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder)holder;
        myViewHolder.className.setText(classNameArray.get(position).getClassName());

    }

    @Override
    public int getItemCount() {
        return classNameArray.size();
    }
}

