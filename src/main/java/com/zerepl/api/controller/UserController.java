package com.zerepl.api.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zerepl.api.entity.User;
import com.zerepl.api.services.UserService;

import java.io.*;

public class UserController implements HttpHandler {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String respText;
            User userId = parseInput(exchange);
            User user = userService.getUser(userId.getId());

            if (user != null) {
                respText = String.format("User id: %s\nUser login: %s", user.getId(), user.getLogin());
            } else {
                respText = "User not found";
            }

            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        } else if ("POST".equals(exchange.getRequestMethod())) {
            User user = parseInput(exchange);

            String id = userService.create(user);

            String respText = String.format("Registered %s with id %s!", user.getLogin(), id);
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        } else if ("DELETE".equals(exchange.getRequestMethod())) {

        } else if ("PUT".equals(exchange.getRequestMethod())) {

        }  else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
    }

    private User parseInput(HttpExchange exchange) throws IOException {
        User user = new User();
        InputStream body = exchange.getRequestBody();

        InputStreamReader isr = new InputStreamReader(body, "utf-8");
        BufferedReader br = new BufferedReader(isr);

        int b;
        StringBuilder buf = new StringBuilder();
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        br.close();
        isr.close();

        String buildBody[] = buf.toString().split("/");

        if ("GET".equals(exchange.getRequestMethod())) {
            user.setId(buildBody[0].split("=")[1]);
        } else {
            user.setLogin(buildBody[0].split("=")[1]);
            user.setPassword(buildBody[1].split("=")[1]);
        }


        return user;
    }
}

