package Functionality.APITesting;

import com.microsoft.playwright.*;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;
import java.net.URI;

public class TestPlay {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/title", new TitleHandler());
        System.out.println("MCP Playwright Server started at http://localhost:" + port);
        server.setExecutor(null);
        server.start();
    }

    static class TitleHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response;
            if (!"GET".equals(exchange.getRequestMethod())) {
                response = "{\"error\": \"Only GET supported\"}";
                exchange.sendResponseHeaders(405, response.getBytes().length);
            } else {
                try {
                    URI uri = exchange.getRequestURI();
                    String query = uri.getQuery();
                    String url = null;
                    if (query != null && query.startsWith("url=")) {
                        url = query.substring(4);
                    }
                    if (url == null || url.isEmpty()) {
                        response = "{\"error\": \"Missing url parameter\"}";
                        exchange.sendResponseHeaders(400, response.getBytes().length);
                    } else {
                        try (Playwright playwright = Playwright.create()) {
                            Browser browser = playwright.chromium().launch();
                            Page page = browser.newPage();
                            page.navigate(url);
                            String title = page.title();
                            response = "{\"title\": \"" + title.replace("\"", "\\\"") + "\"}";
                            browser.close();
                            exchange.sendResponseHeaders(200, response.getBytes().length);
                        }
                    }
                } catch (Exception e) {
                    response = "{\"error\": \"" + e.getMessage().replace("\"", "\\\"") + "\"}";
                    exchange.sendResponseHeaders(500, response.getBytes().length);
                }
            }
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
