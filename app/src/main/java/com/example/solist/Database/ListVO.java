package com.example.solist.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "list_table")
public class ListVO {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String contents;

    private int status;

    private String writeDate;

    public ListVO(String contents, int status, String writeDate) {
        this.contents = contents;
        this.status = status;
        this.writeDate = writeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", contents='" + contents + '\'' +
                ", status='" + status + '\'' +
                ", writeDate='" + writeDate + '\'' +
                '}' + "\n";
    }
}
