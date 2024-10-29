package com.corndel.framerate;

import com.corndel.framerate.models.Movie;
import com.corndel.framerate.models.Review;
import com.corndel.framerate.repositories.MovieRepository;
import com.corndel.framerate.repositories.ReviewRepository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.List;
import java.util.Map;

public class App {
  public static void main(String[] args) {
    var javalin = createApp();
    javalin.start(8081);
  }

  public static Javalin createApp() {
    var app = Javalin.create(
        config -> {
          config.staticFiles.add("/public", Location.CLASSPATH);

          var resolver = new ClassLoaderTemplateResolver();
          resolver.setPrefix("/templates/");
          resolver.setSuffix(".html");
          resolver.setTemplateMode("HTML");

          var engine = new TemplateEngine();
          engine.setTemplateResolver(resolver);

          config.fileRenderer(new JavalinThymeleaf(engine));
        });

    app.get("/", ctx -> {
      //ctx.result("Hello, World!");

        List<Movie> movies = MovieRepository.findAll();

        ctx.render("movies.html", Map.of("movies", movies));
    });

      app.get("/movies/{num}", ctx -> {
          int number = Integer.parseInt(ctx.pathParam("num"));
          Movie movie = MovieRepository.findById(number);
          List<Review> reviews = ReviewRepository.findByMovie(movie.getId());
          ctx.render("movie.html", Map.of("movie", movie, "reviews", reviews));
      });

      app.get("/review/{num}", ctx -> {
          int movieId = Integer.parseInt(ctx.pathParam("num"));
          ctx.render("review/review.html", Map.of("movieId", movieId));
      });


      app.post("/review", ctx -> {
          try {

              int movieId = Integer.parseInt(ctx.formParam("movieId"));
              String content = ctx.formParam("review");
              int rating = Integer.parseInt(ctx.formParam("rating"));

              System.out.println(movieId);
              System.out.println(content);
              System.out.println(rating);
              ReviewRepository.addReview(movieId, content, rating);
              ctx.render("review/reviewapproved.html");

          } catch(Exception e){
              System.err.println(e.getMessage());
              ctx.render("/review/review.html", Map.of("e", e.getMessage()));
          }
      });




      return app;
  }
}
