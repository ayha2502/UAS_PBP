package async;

import android.os.AsyncTask;

import com.syzlnnuro.fmodul1.models.Diary;

import database.DiaryDao;

public class DeleteAsyncTask extends AsyncTask<Diary,Void, Void> {

    private static final String TAG = "DeleteAsyncTask";
    private DiaryDao mDiaryDao;
    public DeleteAsyncTask(DiaryDao dao){
        mDiaryDao = dao;
    }
    @Override
    protected Void doInBackground(Diary... diaries) {
        mDiaryDao.deleteDiaries(diaries);
        return null;
    }
}
