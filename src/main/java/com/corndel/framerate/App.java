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
          //ctx.result("Hello, World!");
          int number = Integer.parseInt(ctx.pathParam("num"));
//          int count = MovieRepository.numberOfMovies();

//          if (count == 0 || number < 0 || number > count) {
//              ctx.json("Incorrect movie issued");
//              return;
//          }

          Movie movie = MovieRepository.findById(number);
          List<Review> reviews = ReviewRepository.findByMovie(movie.getId());

          ctx.render("movie.html", Map.of("movie", movie, "reviews", reviews));
      });





    return app;
  }
}
