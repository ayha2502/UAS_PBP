package database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.syzlnnuro.fmodul1.DiaryDetailsActivity;
import com.syzlnnuro.fmodul1.MainActivity;
import com.syzlnnuro.fmodul1.models.Diary;

import java.util.List;

import async.InsertAsyncTask;

public class DiaryRepository {
    private DiaryDatabase diaryDatabase;

    public DiaryRepository(Context context) {

    }

//    public DiaryRepository(MainActivity mainActivity) {
//    }

//    public DiaryRepository(DiaryDetailsActivity diaryDetailsActivity) {
//
//    }


    public void DiaryDatabase(Context context){
        diaryDatabase = DiaryDatabase.getInstance(context);
    }
    public void insertDiaryTask(Diary diary){
        new InsertAsyncTask(diaryDatabase.getDiaryDao()).execute(diary);
    }
    public void updateDiaryTask(Diary diary){

    }
    public LiveData<List<Diary>> retrieveDiaryTask(){
        return diaryDatabase.getDiaryDao().getDiaries();
    }
    public void deleteDiaryTask(Diary diary){

    }
}
