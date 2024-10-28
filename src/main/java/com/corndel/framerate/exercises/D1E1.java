package com.corndel.framerate.exercises;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class D1E1 {

  public static Javalin createApp() {
    var app = Javalin.create(
        config -> {
          // TODO: Configure the app to serve static files from
            config.staticFiles.add("exercises/public", Location.CLASSPATH);
          // 'resources/exercises/public'
        });

    return app;
  }
}
