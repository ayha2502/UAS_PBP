package com.syzlnnuro.fmodul1.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "diaries")
public class Diary implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String content;
//    @ColumnInfo
//    private Date date;
    @ColumnInfo
    private String timestamp;

    public Diary() {

    }

    public Diary(String title, String content, Date date, String timestamp) {
        this.title = title;
        this.content = content;
        //this.date = date;
        this.timestamp = timestamp;
    }

    public Diary(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        //date = (Date) in.readSerializable();
        timestamp = in.readString();
    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };

    public Diary(Diary diary) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public Date getDate() {
//        //return date;
//    }

    public void setDate(Date date) {
        //this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                //", date=" + date +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        //dest.writeSerializable(date);
        dest.writeString(timestamp);
    }
}
