package edu.escuelaing.arep;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WebServer {
    private static final int PORT = 32500;
    private static final String WEB_ROOT = "src/main/resources/public";
    private static final Map<String, String> restaurants = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor web iniciado en el puerto " + PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleRequest(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        InputStream input = clientSocket.getInputStream();
        OutputStream output = clientSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String requestLine = reader.readLine();
        if (requestLine == null) return;

        System.out.println("Solicitud recibida: " + requestLine);
        String[] parts = requestLine.split(" ");
        if (parts.length < 2) return;

        String method = parts[0];
        String resource = parts[1];

        if (resource.equals("/")) resource = "/index.html";

        if (resource.startsWith("/api/")) {
            handleApiRequest(method, resource, reader, output);
        } else {
            serveStaticFile(resource, output);
        }
    }

    private static void serveStaticFile(String resource, OutputStream output) throws IOException {
        File file = new File(WEB_ROOT + resource);
        if (file.exists() && !file.isDirectory()) {
            sendResponse(output, 200, "OK", Files.readAllBytes(file.toPath()), getMimeType(file.getName()));
        } else {
            String notFoundMessage = "<html><body><h1>404 Not Found</h1></body></html>";
            sendResponse(output, 404, "Not Found", notFoundMessage.getBytes(), "text/html");
        }
    }

    private static void handleApiRequest(String method, String resource, BufferedReader reader, OutputStream output) throws IOException {
        if ("GET".equals(method) && resource.equals("/api/restaurants")) {
            String json = new ArrayList<>(restaurants.values()).toString();
            sendResponse(output, 200, "OK", json.getBytes(), "application/json");
        } else if ("POST".equals(method) && resource.startsWith("/api/restaurants")) {
            String requestBody = reader.readLine();
            if (requestBody != null) {
                restaurants.put(UUID.randomUUID().toString(), requestBody);
                sendResponse(output, 201, "Created", "{\"status\":\"success\"}".getBytes(), "application/json");
            }
        } else if ("DELETE".equals(method) && resource.startsWith("/api/restaurants/")) {
            String id = resource.substring(16);
            restaurants.remove(id);
            sendResponse(output, 200, "OK", "{\"status\":\"deleted\"}".getBytes(), "application/json");
        } else {
            sendResponse(output, 400, "Bad Request", "{\"error\":\"Invalid request\"}".getBytes(), "application/json");
        }
    }


    private static void sendResponse(OutputStream output, int statusCode, String statusMessage, byte[] content, String contentType) throws IOException {
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + content.length);
        writer.println("Connection: close");
        writer.println();
        output.write(content);
        output.flush();
    }

    private static String getMimeType(String fileName) {
        if (fileName.endsWith(".html")) return "text/html";
        if (fileName.endsWith(".css")) return "text/css";
        if (fileName.endsWith(".js")) return "application/javascript";
        if (fileName.endsWith(".jpg")) return "image/jpeg";
        if (fileName.endsWith(".png")) return "image/png";
        return "application/octet-stream";
    }
}

