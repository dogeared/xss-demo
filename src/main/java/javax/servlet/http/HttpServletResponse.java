package javax.servlet.http;

import java.io.IOException;
import java.io.PrintWriter;

public class HttpServletResponse {
    private final jakarta.servlet.http.HttpServletResponse newResponse;

    public HttpServletResponse(jakarta.servlet.http.HttpServletResponse response) {
        this.newResponse = response;
    }

    public void setContentType(String type) {
        newResponse.setContentType(type);
    }

    public PrintWriter getWriter() throws IOException {
        return newResponse.getWriter();
    }
}
