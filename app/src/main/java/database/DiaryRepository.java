package database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.syzlnnuro.fmodul1.models.Diary;

import java.util.Date;
import java.util.List;

import async.DeleteAsyncTask;
import async.InsertAsyncTask;
import async.UpdateAsyncTask;
import database.DiaryDatabase;

public class DiaryRepository {
    private DiaryDatabase diaryDatabase;
    private LiveData<List<Diary>> allDiaries;

    public DiaryRepository(Context context){
        diaryDatabase = DiaryDatabase.getInstance(context);
        allDiaries = diaryDatabase.getDiaryDao().getDiaries();
    }

    public LiveData<List<Diary>> getAllDiaries() {
        return allDiaries;
    }

    public void updateDiaryTask(Diary diary) {

        new UpdateAsyncTask(diaryDatabase.getDiaryDao()).execute(diary);
    }

    public void deleteDiary(Diary diary){
        new DeleteAsyncTask(diaryDatabase.getDiaryDao()).execute(diary);
    }

    public void insertDiaryTask(Diary diary) {
//        Date currentTime = new Date();
//        Diary diary = new Diary(title, content, currentTime);

        new InsertAsyncTask(diaryDatabase.getDiaryDao()).execute(diary);
    }
}
