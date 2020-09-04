package com.example.memotoy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MemoYJ {
    @PrimaryKey(autoGenerate = true)
    private int seq;
    private String mainText;
    private String subText;
    private int isDone;

//    public MemoYJ(int seq, String mainText, String subText, int isDone) {
//        this.seq = seq;
//        this.mainText = mainText;
//        this.subText = subText;
//        this.isDone = isDone;
//    }

    public MemoYJ(String mainText, String subText, int isDone) {
        this.mainText = mainText;
        this.subText = subText;
        this.isDone = isDone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }
}
