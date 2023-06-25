package com.syzlnnuro.fmodul1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syzlnnuro.fmodul1.adapters.DiaryRecyclerViewAdapter;
import com.syzlnnuro.fmodul1.models.Diary;

import java.util.ArrayList;
import java.util.List;

import database.DiaryRepository;

public class MainActivity extends AppCompatActivity implements DiaryRecyclerViewAdapter.OnDiaryListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private ArrayList<Diary> diaryList = new ArrayList<>();

    private DiaryRecyclerViewAdapter adapter;
    private FloatingActionButton fab;
    private DiaryRepository diaryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.diary_rv);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        diaryRepository = new DiaryRepository(this);

        initRecyclerView();
        // insertDummyData();
        retrieveDiaryData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("My Personal Diary");
        //setSupportActionBar(toolbar);
    }

    private void retrieveDiaryData() {
        diaryRepository.getAllDiaries().observe(this, new Observer<List<Diary>>() {
            @Override
            public void onChanged(List<Diary> diaries) {
                if (diaryList.size() > 0) {
                    diaryList.clear();
                }
                if (diaries != null) {
                    diaryList.addAll(diaries);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteDiary(diaryList.get(viewHolder.getAdapterPosition()));
        }
    };

    private void deleteDiary(Diary diary) {
        diaryList.remove(diary);
        adapter.notifyDataSetChanged();
        diaryRepository.deleteDiary(diary);
    }
    private void updateDiary(Diary diary) {
        Intent intent = new Intent(MainActivity.this, DiaryDetailsActivity.class);
        intent.putExtra("diary", diary);
        intent.putExtra("diary_page", diaryList.indexOf(diary));
        startActivityForResult(intent, DiaryDetailsActivity.REQUEST_CODE_UPDATE_DIARY);
        diaryRepository.updateDiaryTask(diary);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DiaryDetailsActivity.REQUEST_CODE_UPDATE_DIARY && resultCode == RESULT_OK && data != null) {
            Diary updatedDiary = data.getParcelableExtra("updated_diary");
            int diaryIndex = data.getIntExtra("diary_index", -1);

            if (diaryIndex != -1 && updatedDiary != null) {
                diaryList.set(diaryIndex, updatedDiary);
                adapter.notifyDataSetChanged();
            }
        }
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DiaryRecyclerViewAdapter(diaryList, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, DiaryDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDiaryClick(int position) {
        //Toast.makeText(this, "Anda memilih diari ke-" + (position + 1), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, DiaryDetailsActivity.class);
        intent.putExtra("diary", diaryList.get(position));
        intent.putExtra("diary_page", position);
        startActivityForResult(intent, DiaryDetailsActivity.REQUEST_CODE_UPDATE_DIARY);
    }
}
