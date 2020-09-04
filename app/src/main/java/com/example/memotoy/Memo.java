package com.example.memotoy;

import java.io.Serializable;

public class Memo implements Serializable {

    int seq;
    String maintext;
    String subtext;
    int isdone;

    public Memo(int seq, String maintext, String subtext, int isdone) {
        this.seq = seq;
        this.maintext = maintext;
        this.subtext = subtext;
        this.isdone = isdone;
    }

    public Memo(String maintext, String subtext, int isdone) {
        this.maintext = maintext;   //메모
        this.subtext = subtext;     //날짜
        this.isdone = isdone;       //완료 여부
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }
}
