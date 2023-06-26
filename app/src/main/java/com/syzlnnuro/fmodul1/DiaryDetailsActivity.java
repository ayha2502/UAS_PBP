package com.syzlnnuro.fmodul1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.syzlnnuro.fmodul1.models.Diary;

import java.util.Date;
import java.util.List;

import database.DiaryRepository;
import utils.DateUtil;

public class DiaryDetailsActivity extends AppCompatActivity
        implements View.OnTouchListener, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {


    public static final int REQUEST_CODE_UPDATE_DIARY = 1;
    private static final String TAG = "DiaryDetailsActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;
    private int page;
    private Diary diary;
    private EditText et_diary_content;
    private EditText et_toolbar_edit;
    private TextView text_toolbar_view;
    private RelativeLayout rl_back, rl_check;
    private ImageButton ib_check, ib_back;
    private TextView titleView;
    private EditText editView;
    private int diaryMode;
    private boolean isNewDiary;
    private DateUtil setDate;
    private GestureDetector gestureDetector;
    private DiaryRepository diaryRepository;
    private Diary finalDiary;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_details);

        et_diary_content = findViewById(R.id.contentDiary);
        et_toolbar_edit = findViewById(R.id.title_edit);
        text_toolbar_view = findViewById(R.id.title_view);

        rl_back = findViewById(R.id.back_button);
        rl_check = findViewById(R.id.check_button);
        ib_back = findViewById(R.id.toolbar_back_button);
        ib_check = findViewById(R.id.toolbar_check_button);
        titleView = findViewById(R.id.title_view);
        editView = findViewById(R.id.title_edit);

        diaryRepository = new DiaryRepository(this);

        diaryRepository.getAllDiaries().observe(this, new Observer<List<Diary>>() {

            @Override
            public void onChanged(List<Diary> diaries) {

            }
        });
        if (getIncomingIntent()) {
            // Diary baru (edit mode)
            setNewDiaryProperties();
            enableEditMode();
        } else {
            // Diary lama (view mode)
            setDiaryProperties();
            disableContentInteraction();
        }
        setListener();
    }

    private void saveChanges() {
        if (isNewDiary) {
            saveNewDiary();
        } else {
            updateDiary();
        }
    }

    private void saveNewDiary() {
        diaryRepository.insertDiaryTask(finalDiary);
    }

    private void updateDiary() {
        diaryRepository.updateDiaryTask(finalDiary);
    }

    private void hideVirtualKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void enableEditMode() {
        rl_back.setVisibility(View.GONE);
        rl_check.setVisibility(View.VISIBLE);

        titleView.setVisibility(View.GONE);
        editView.setVisibility(View.VISIBLE);

        diaryMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disableEditMode() {
        rl_back.setVisibility(View.VISIBLE);
        rl_check.setVisibility(View.GONE);

        titleView.setVisibility(View.VISIBLE);
        editView.setVisibility(View.GONE);

        diaryMode = EDIT_MODE_DISABLED;
        disableContentInteraction();
        saveChanges();
    }

    private void setListener() {
        et_diary_content.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);
        ib_check.setOnClickListener(this);
        titleView.setOnClickListener(this);
        ib_back.setOnClickListener(this);
        editView.addTextChangedListener(this);
    }

    private void setDiaryProperties() {
        et_toolbar_edit.setText(diary.getTitle());
        text_toolbar_view.setText(diary.getTitle());
        et_diary_content.setText(diary.getContent()); // Update this line
    }

    private void setNewDiaryProperties() {
        et_toolbar_edit.setText("New Diary");
        text_toolbar_view.setText("New Diary");

        diary = new Diary();
        finalDiary = new Diary(diary);
        //diary.setDate(DateUtil.getCurrentTimestamp());
        //finalDiary.setDate(DateUtil.getCurrentTimestamp());

        diary.setContent(""); // Add this line to set the initial content
        finalDiary.setContent(""); // Add this line to set the initial content
    }



    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("diary")) {
            diary = getIntent().getParcelableExtra("diary");
            finalDiary = getIntent().getParcelableExtra("diary");
            diaryMode = EDIT_MODE_DISABLED;
            isNewDiary = false;
            return false;
        }
        diaryMode = EDIT_MODE_ENABLED;
        isNewDiary = true;
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onSingleTapConfirmed(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(@NonNull MotionEvent motionEvent) {
        enableEditMode();
        Toast.makeText(this, "Double Click Terdeteksi!", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(view.getId()== R.id.toolbar_check_button) {
            hideVirtualKeyboard();
            disableEditMode();
            // Mengambil data dari form input diary
            String title = et_toolbar_edit.getText().toString().trim();
            String content = et_diary_content.getText().toString().trim();

            // Mengecek apakah input diary tidak kosong
            if (!title.isEmpty() && !content.isEmpty()) {
                // Membuat objek Diary baru
                Diary diary = new Diary();
                diary.setTitle(title);
                diary.setContent(content);
                diary.setDate(new Date());

                // Memanggil metode insertDiaryTask() pada DiaryRepository untuk menyimpan diary ke database
                diaryRepository.insertDiaryTask(diary);

                // Menampilkan pesan berhasil
                Toast.makeText(this, "Diary saved successfully", Toast.LENGTH_SHORT).show();

                // Kembali ke MainActivity
                finish();
            } else {
                // Menampilkan pesan error jika input diary kosong
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
            saveChanges();
        }else if (id == R.id.title_view) {
            enableEditMode();
            et_toolbar_edit.requestFocus();
            et_toolbar_edit.setSelection(et_toolbar_edit.length());
        } else if (id == R.id.toolbar_back_button) {
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (diaryMode == EDIT_MODE_ENABLED) {
            onClick(ib_back);
        } else {
            super.onBackPressed();
        }
    }

    private void disableContentInteraction() {
        et_diary_content.setKeyListener(null);
        et_diary_content.setFocusable(false);
        et_diary_content.setFocusableInTouchMode(false);
        et_diary_content.setCursorVisible(false);
        et_diary_content.clearFocus();
    }

    private void enableContentInteraction() {
        et_diary_content.setKeyListener(new EditText(this).getKeyListener());
        et_diary_content.setFocusable(true);
        et_diary_content.setFocusableInTouchMode(true);
        et_diary_content.setCursorVisible(true);
        et_diary_content.clearFocus();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", diaryMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        diaryMode = savedInstanceState.getInt("mode");
        if (diaryMode == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        titleView.setText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
