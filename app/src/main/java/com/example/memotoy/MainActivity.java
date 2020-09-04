package com.example.memotoy;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memotoy.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //SQLiteHelper dbHelper;
    private AppDatabase db;
    private ActivityMainBinding binding;
    RecyclerView recyclerView;

    RecyclerAdapter recyclerAdapter;
    //Button btnAdd;

    List<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = binding.recyclerview;

        //dbHelper = new SQLiteHelper(MainActivity.this);
        //memoList = dbHelper.selectAll();

        //데이터베이스 생성
        db = Room.databaseBuilder(this, AppDatabase.class, "MemoDB")
                .allowMainThreadQueries()
                .build();

        memoList = db.memoDao().getAll();

        //LinearLaoutManager를 통해 레이아웃 모양 결정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);

        // 새로운 메모 작성
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    //Intent 끝난 후, Intent에 포함된 데이터를 가지고 onActivityResult 실행된다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){
            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");

            Memo memo = new Memo(strMain, strSub, 0);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();

            //dbHelper.insertMemo(memo);
            db.memoDao().insert(memo);
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

        private List<Memo> listdata;


        public RecyclerAdapter(List<Memo> listdata){
            this.listdata = listdata;
        }

        //RecyclerAdapter에서 extends ItemViewHolder Override한다.
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

        //onBindViewHolder로 연결한다.
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i)  {

            final Memo memo = listdata.get(i);

            itemViewHolder.mainText.setTag(memo.getSeq());

            itemViewHolder.mainText.setText(memo.getMainText());
            itemViewHolder.subText.setText(memo.getSubText());

            if (memo.getIsDone() == 0){
                itemViewHolder.img.setBackgroundColor(Color.LTGRAY);
            }else if (memo.getIsDone() == 1){
                itemViewHolder.img.setBackgroundColor(Color.GREEN);
            }else if (memo.getIsDone() == 2){
                itemViewHolder.img.setBackgroundColor(Color.MAGENTA);
            }

        }

        void addItem(Memo memo){
            listdata.add(memo);
        }
        void removeItem(int position){
            listdata.remove(position);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            private TextView mainText;
            private TextView subText;
            private ImageView img;

            public ItemViewHolder(@NonNull final View itemView){
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
                            //db.memoDao().delete();
                            db.memoDao().deleteBySeq(seq);
                            removeItem(position);
                            notifyDataSetChanged();
                        }
                        return false;
                    }
                });

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        int seq = (int)mainText.getTag();
                        if(position != RecyclerView.NO_POSITION){
                            Memo nowMemo = db.memoDao().selectBySeq(seq);
                            int nextNum = nowMemo.getIsDone() + 1;
                            //Toast.makeText(getApplicationContext(), "nextNum : " + nextNum + "seq : " + seq , Toast.LENGTH_LONG).show();
                            if (nextNum > 2)
                                nextNum = 0;
                            db.memoDao().updateIsDone(seq, nextNum);
                            notifyDataSetChanged();
                        }
                    }
                });

            }

        }
    }

}
