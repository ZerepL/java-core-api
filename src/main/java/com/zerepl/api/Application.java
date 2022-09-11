package com.zerepl.api;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.zerepl.api.controller.UserController;
import com.zerepl.api.data.UsersData;
import com.zerepl.api.repository.UserRepository;
import com.zerepl.api.services.UserService;


class Application {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;

        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        UserRepository userRepository = new UsersData();

        UserService userService = new UserService(userRepository);

        server.createContext("/users", new UserController(userService));

        server.setExecutor(null);
        server.start();
    }
}