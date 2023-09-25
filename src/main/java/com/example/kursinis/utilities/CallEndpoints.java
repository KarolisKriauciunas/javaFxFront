package com.example.kursinis.utilities;

import java.io.*;
import java.net.*;
import java.net.http.*;

public class CallEndpoints {
    public static String Get(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            var client = HttpClient.newHttpClient();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String Delete(String url) {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();
            var client = HttpClient.newHttpClient();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return String.valueOf(response.statusCode());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return "500";
    }

    public static String Post(String url, String body) {
        try {
            byte[] sampleData = body.getBytes();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(sampleData))
                    .build();

            var client = HttpClient.newHttpClient();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return String.valueOf(response.statusCode());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String Put(String url, String body) {
        try {
            byte[] sampleData = body.getBytes();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofByteArray(sampleData))
                    .build();
            var client = HttpClient.newHttpClient();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return String.valueOf(response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return "500";
    }
}
