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

    private String createUser(User user) {
        return userService.createUser(user);
    }

    private User getUser(String id) {
        return userService.getUser(id);
    }

    private void deleteUser(String id) {
        userService.deleteUser(id);
    }

    private void updateUser(User user) {
        userService.updateUser(user);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            User user = getUser(parseInput(exchange).getId());
            String respText;

            if (user == null) {
                respText = String.format("user %s dont exist", user.getId());
                writeOutput(exchange, respText, 404);
            } else {
                respText = String.format("User id: %s\nUser name: %s", user.getId(),  user.getName());
                writeOutput(exchange, respText, 200);
            }

        } else if ("POST".equals(exchange.getRequestMethod())) {
            String id = createUser(parseInput(exchange));

            String respText = String.format("user created with id: %s", id);
            writeOutput(exchange, respText, 200);

        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            User user = parseInput(exchange);
            deleteUser(user.getId());
            String respText;

            if (user != null) {
                respText = String.format("user %s dont exist", user.getId());
                writeOutput(exchange, respText, 404);
            } else {
                respText = String.format("%s deleted", user.getId());
                writeOutput(exchange, respText, 200);
            }
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            User user = parseInput(exchange);
            updateUser(user);
            String respText;

            if (user != null) {
                respText = String.format("user %s dont exist", user.getId());
                writeOutput(exchange, respText, 404);
            } else {
                respText = String.format("%s updated", user.getId());
                writeOutput(exchange, respText, 200);
            }

        }  else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
    }

    private User parseInput(HttpExchange exchange) throws IOException {
        User user = new User();
        InputStream is = exchange.getRequestBody();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuffer response = new StringBuffer();
        while((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();

        String buildBody[] = response.toString().split("/");

        if ("GET".equals(exchange.getRequestMethod()) || "DELETE".equals(exchange.getRequestMethod())) {
            user.setId(buildBody[0].split("=")[1]);
        } else {
            user.setName(buildBody[0].split("=")[1]);
            user.setPassword(buildBody[1].split("=")[1]);
        }

        return user;
    }

    private void writeOutput(HttpExchange exchange, String respText, int code) throws IOException {
        exchange.sendResponseHeaders(code, respText.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(respText.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}

