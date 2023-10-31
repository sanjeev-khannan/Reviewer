package com.reviewer.pojos;

public class ReviewCounts {

    private int likes = 0;
    private int dislikes = 0;

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public void incrementLikes() {
        likes++;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void incrementDislikes() {
        dislikes++;
    }
}
