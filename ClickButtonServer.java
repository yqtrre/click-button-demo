import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ClickButtonServer {

    private static final String PAGE = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1">
              <title>Click Button Demo</title>
              <style>
                body { font-family: Arial, sans-serif; display: flex; flex-direction: column;
                       align-items: center; justify-content: center; height: 100vh; margin: 0; }
                button { font-size: 20px; padding: 14px 32px; cursor: pointer; }
                #message { margin-top: 24px; font-size: 22px; color: #1a7f37; }
              </style>
            </head>
            <body>
              <button id="btn">Press me</button>
              <div id="message"></div>
              <script>
                document.getElementById("btn").addEventListener("click", async () => {
                  const res = await fetch("/click");
                  document.getElementById("message").textContent = await res.text();
                });
              </script>
            </body>
            </html>
            """;

    public static void main(String[] args) throws IOException {
        // Hosting services usually give the port in the PORT environment variable
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8080;

        // Listen on all network interfaces so other devices can reach it
        HttpServer server = new HttpServer_Helper().create(port);

        // The page with the button
        server.createContext("/", exchange -> send(exchange, 200, "text/html; charset=UTF-8", PAGE));

        // Called when the button is clicked (same as actionPerformed in your example)
        server.createContext("/click", exchange -> {
            System.out.println("LOPP AYANGG SOO MUCHHH !!!!");   // shows in the server console
            send(exchange, 200, "text/plain; charset=UTF-8", "LOPP AYANGG SOO MUCHHH !!!!");
        });

        server.start();
        System.out.println("Server running on port " + port);
        System.out.println("Open http://localhost:" + port + " in your browser");
    }

    private static void send(HttpExchange exchange, int status, String type, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", type);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    // Small helper so main() stays easy to read
    private static class HttpServer_Helper {
        HttpServer create(int port) throws IOException {
            return HttpServer.create(new InetSocketAddress(port), 0);
        }
    }
}
