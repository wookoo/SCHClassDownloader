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
    public  OnItemClickListener mListener = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    /*
    public static ArrayList<ClassNameData> classNameArray;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView className;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String posClassName = classNameArray.get(pos).getClassURL();
                    Log.d("아이템 눌렸다","아무튼 눌림 " + pos);
                    Log.d("아이템 누른거",posClassName);
                }
            });
        }

    }*/

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView className;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    mListener.onItemClick(view,pos);
                }
            });


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

