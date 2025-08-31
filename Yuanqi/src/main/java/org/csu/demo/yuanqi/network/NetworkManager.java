package org.csu.demo.yuanqi.network;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NetworkManager {

    // 使用单例模式
    private static final NetworkManager INSTANCE = new NetworkManager();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private final String serverUrl = "http://localhost:8080/api/auth"; // 您的SpringBoot服务器地址

    private NetworkManager() {}

    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    // 使用 record 定义一个 DTO (数据传输对象)，与后端 AuthRequest 匹配
    private record AuthRequest(String username, String password) {}

    // 注册方法
    public String register(String username, String password) throws Exception {
        AuthRequest requestBody = new AuthRequest(username, password);
        String jsonBody = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // 登录方法
    public String login(String username, String password) throws Exception {
        AuthRequest requestBody = new AuthRequest(username, password);
        String jsonBody = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // 您可以检查 response.statusCode() == 200 来判断是否成功
        return response.body();
    }
}