//package com.example.glass.voicerecognitionsample;
//
//import android.content.Context;
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.io.File;
//import java.net.URI;
//import java.util.ArrayList;
//
//public class AudioAdapter extends RecyclerView {
//    ArrayList<Uri> dataModels;
//    Context context;
//
//    public AudioAdapter(Context context, ArrayList<Uri> dataModels)
//    {
//        super(context);
//        this.dataModels = dataModels;
//        this.context = context;
//    }
//
//    public int getItemCount()
//    {
//        return dataModels.size();
//    }
//
//    @NonNull
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//    {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclrrview_item, parent, false);
//        MyViewHolder viewHolder = new MyViewHolder(view);
//
//        return viewHolder;
//    }
//
//    public void noBindViewHolder(@NonNull ViewHolder holder, int position) {
//        MyViewHolder myViewHolder = (MyViewHolder) holder;
//
//        String uriname = String.valueOf(dataModels.get(position));
//        File file = new File(uriname);
//
//        myViewHolder.audioTitle.setText(file.getName());
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView audioTitle;
//
//        public MyViewHolder(@NonNull View itemView)
//        {
//            super(itemView);
//            audioTitle = itemView.findViewById(R.id.audioTitle);
//
//        }
//
//    }
//
//
//}
