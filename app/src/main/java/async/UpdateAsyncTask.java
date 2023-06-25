package async;

import android.os.AsyncTask;

import androidx.room.Update;

import com.syzlnnuro.fmodul1.models.Diary;

import database.DiaryDao;

public class UpdateAsyncTask extends AsyncTask<Diary, Void, Void> {
    private static final String TAG = "UpdateAsyncTask";
    private DiaryDao mDiaryDao;
    public UpdateAsyncTask(DiaryDao dao){
        mDiaryDao = dao;
    }
    @Override
    protected Void doInBackground(Diary... diaries) {
        mDiaryDao.updateDiaries(diaries);
        return null;
    }
}
