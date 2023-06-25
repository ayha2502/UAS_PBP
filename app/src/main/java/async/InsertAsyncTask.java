package async;

import android.os.AsyncTask;

import androidx.loader.content.AsyncTaskLoader;

import com.syzlnnuro.fmodul1.models.Diary;

import database.DiaryDao;

public class InsertAsyncTask extends AsyncTask<Diary, Void, Void> {
    private DiaryDao mDiaryDao;
    public InsertAsyncTask(DiaryDao diaryDao){
        mDiaryDao = diaryDao;
    }
    @Override
    protected Void doInBackground(Diary... diaries) {
        mDiaryDao.insertDiaries(diaries);
        return null;
    }
}
