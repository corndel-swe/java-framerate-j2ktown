package com.corndel.framerate.models;

import java.util.List;

public class Review {
  private int id;
  public int movieId;
  public long createdAt;
  public int rating;
  public String content;



  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Review(){}

  public Review(int id, int movieId, long createdAt, int rating, String content) {

  }
}
