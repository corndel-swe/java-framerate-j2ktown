package com.corndel.framerate.exercises;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

public class D2E3 {
  public static Javalin createApp() {
    var app = Javalin.create(
        config -> {
          config.staticFiles.add("/exercises/public", Location.CLASSPATH);

          var resolver = new ClassLoaderTemplateResolver();
          resolver.setPrefix("/exercises/templates/");
          resolver.setSuffix(".html");
          resolver.setTemplateMode("HTML");

          var engine = new TemplateEngine();
          engine.setTemplateResolver(resolver);

          config.fileRenderer(new JavalinThymeleaf(engine));
        });

    app.get(
        "/d2e3",
        ctx -> {
          ctx.render("d2e3.html");
        });

    app.post(
        "/submit",
        ctx -> {
          // TODO: Open d2e3.html and follow the instructions

          // TODO: get the `name` and `email` from the form so that the below response
          // works

            String name = ctx.formParamAsClass("name", String.class).get();
            String email = ctx.formParamAsClass("email", String.class).get();
            System.out.println(name);
            ctx.result("Received: " + name + ", " + email);
        });

    return app;
  }

    public static void main(String[] args) {
        var javalin = createApp();
        javalin.start(8081);
    }
}
