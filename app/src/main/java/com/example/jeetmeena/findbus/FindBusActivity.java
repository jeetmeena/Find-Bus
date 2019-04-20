package com.example.jeetmeena.findbus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

public class FindBusActivity extends AppCompatActivity {
 RecyclerView mRecyclerView;
 String[] arrayList;
  RecyclerView.Adapter adapter;
  RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bus);
        mRecyclerView=findViewById(R.id.recyclerBus);
        layoutManager=new LinearLayoutManager(this);
        arrayList=new String[]{"Aweesdfe","Sfvseerfa","Gadssweeff","Hweeqew","QEEEddf","Bfgaafwe","Nagargra","aDFRfegr",
                "Aweesdfe","Sfvseerfa","Gadssweeff","Hweeqew","QEEEddf","Bfgaafwe","Nagargra","aDFRfegr"};


        adapter=new   BusListRecyclerViews(arrayList,FindBusActivity.this);
       mRecyclerView.setLayoutManager(layoutManager);
       mRecyclerView.setAdapter(adapter);



    }


    private class BusListRecyclerViews extends RecyclerView.Adapter<BusListRecyclerViews.ViewHolder> {
        String[] arrayz;
        public BusListRecyclerViews(String[] arrayList, FindBusActivity findBusActivity) {
            arrayz=arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.busitemlist,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return arrayz.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
            public ViewHolder(View itemView) {
                super(itemView);
            }
            @Override
            public void onClick(View v) {
                if(R.id.nameOfBus==v.getId()){
                    Toast.makeText( FindBusActivity.this,"name",Toast.LENGTH_SHORT).show();

                }
                else if(R.id.destenas==v.getId()){
                    Toast.makeText( FindBusActivity.this,"button",Toast.LENGTH_SHORT).show();
                }

                Toast.makeText( FindBusActivity.this,"item",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
