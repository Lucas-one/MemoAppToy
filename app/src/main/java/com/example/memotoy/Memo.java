package com.example.memotoy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Memo {
    @PrimaryKey(autoGenerate = true)
    int seq;
    String mainText;
    String subText;
    int isDone;

//    public Memo(int seq, String mainText, String subText, int isDone) {
//        this.seq = seq;
//        this.mainText = mainText;
//        this.subText = subText;
//        this.isDone = isDone;
//    }

    public Memo(String mainText, String subText, int isDone) {
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
