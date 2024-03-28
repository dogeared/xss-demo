package com.example.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@SpringBootApplication
@Controller
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // This will NOT get caught by snyk code
    // I suspect this has something to with HttpServletResponse being moved to the
    // jakarta.servlet.http package
    @GetMapping("/xss")
    public void xss(@RequestParam String param, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.write("<div class=\"panel-heading\"><h1>" + param + "</h1></div>");
        writer.flush();
    }


    // This WILL get caught by snyk code
    // It uses a fake wrapper around HttpServletResponse
    // in the old package location - javax.servlet.http
    @GetMapping("/old-xss")
    public void oldXss(@RequestParam String param, HttpServletResponse response) throws IOException {
        javax.servlet.http.HttpServletResponse newResponse =
            new javax.servlet.http.HttpServletResponse(response);
        newResponse.setContentType("text/html");
        var writer = newResponse.getWriter();
        writer.write("<div class=\"panel-heading\"><h1>" + param + "</h1></div>");
        writer.flush();
    }
}
