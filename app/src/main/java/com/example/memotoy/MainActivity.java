package com.example.memotoy;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.memotoy.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //SQLiteHelper dbHelper;
    private AppDatabase db;
    private ActivityMainBinding binding;
    RecyclerView recyclerView;

    RecyclerAdapter recyclerAdapter;
    Button btnAdd;

    List<MemoYJ> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        btnAdd = binding.btnAdd;
        recyclerView = binding.recyclerview;

        //dbHelper = new SQLiteHelper(MainActivity.this);
        //memoList = dbHelper.selectAll();

        db = Room.databaseBuilder(this, AppDatabase.class, "MemoDB")
                .allowMainThreadQueries()
                .build();
        memoList = db.memoYJDao().getAll();

        //recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);
        //btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 새로운 메모 작성
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){
            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");

            MemoYJ memo = new MemoYJ(strMain, strSub, 0);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();

            //dbHelper.insertMemo(memo);
            db.memoYJDao().insert(memo);
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

        private List<MemoYJ> listdata;

        public RecyclerAdapter(List<MemoYJ> listdata){
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            MemoYJ memo = listdata.get(i);

            itemViewHolder.mainText.setTag(memo.getSeq());

            itemViewHolder.mainText.setText(memo.getMainText());
            itemViewHolder.subText.setText(memo.getSubText());

            if (memo.getIsDone() == 0){
                itemViewHolder.img.setBackgroundColor(Color.LTGRAY);
            }else{
                itemViewHolder.img.setBackgroundColor(Color.GREEN);
            }
        }

        void addItem(MemoYJ memo){
            listdata.add(memo);
        }
        void removeItem(int position){
            listdata.remove(position);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            private TextView mainText;
            private TextView subText;
            private ImageView img;

            public ItemViewHolder(@NonNull View itemView){
                super(itemView);

                mainText = itemView.findViewById(R.id.item_maintext);
                subText = itemView.findViewById(R.id.item_subtext);
                img = itemView.findViewById(R.id.item_image);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        int position = getAdapterPosition();
                        int seq = (int)mainText.getTag();
                        if(position != RecyclerView.NO_POSITION){
                            //dbHelper.deleteMemo(seq);
                            //db.memoYJDao().delete();
                            removeItem(position);
                            notifyDataSetChanged();
                        }

                        return false;
                    }
                });

            }

        }
    }

}
