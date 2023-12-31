package database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.syzlnnuro.fmodul1.models.Diary;

import java.util.List;

@Dao
public interface DiaryDao {
    @Insert
    long[] insertDiaries(Diary...diaries);
    @Delete
    int deleteDiaries (Diary...diaries);
    @Update
    int updateDiaries(Diary...diaries);
    @Query("SELECT * FROM diaries")
    LiveData<List<Diary>> getDiaries();
    @Query("SELECT * FROM diaries WHERE title LIKE :title")
    List<Diary> getDiaryWithCustomQuery(String title);


}
