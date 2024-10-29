package com.corndel.framerate.repositories;

import com.corndel.framerate.DB;
import com.corndel.framerate.models.Movie;
import com.corndel.framerate.models.Review;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {
  public static List<Review> findByMovie(int movieId) throws SQLException {
    var query = "SELECT * FROM REVIEWS WHERE movieId = ?";

    try (var con = DB.getConnection();
         var stmt = con.prepareStatement(query)) {
      stmt.setInt(1, movieId);
      try (var rs = stmt.executeQuery()) {
        var reviews = new ArrayList<Review>();
        while (rs.next()) {

          var id = rs.getInt("id");
          var createdAt = rs.getLong("createdAt");
          var content = rs.getString("content");
          var rating = rs.getInt("rating");

          reviews.add(new Review(id, movieId, createdAt, rating, content));


        }
        return reviews;
      }
    }
  }

  public static void addReview(int movieId, String review, int rating) throws SQLException {

    Movie movie = MovieRepository.findById(movieId);
    if (movie == null) {
      throw new Error("Movie not found");
    }

    String query = "INSERT INTO reviews (movieId, content, rating) VALUES (?, ?, ?)";

    try (var con = DB.getConnection();
         var stmt = con.prepareStatement(query)) {

      stmt.setInt(1, movieId);
      stmt.setString(2, review);
      stmt.setInt(3, rating);

      int rowsAffected = stmt.executeUpdate();

      if (rowsAffected <= 0) {
        throw new Error("Movie not added");
      }
    }
  }
}


