package com.nhnacademy.myserver.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
    String responseHeader;
    String responseBody;
    String inetAddress;
    Request request;

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> map = new LinkedHashMap<>();

    public Response(Request request, String inetAddress) {
        this.request = request;
        this.inetAddress = inetAddress;
        this.responseHeader = makeHeader(request);
    }

    private String makeHeader(Request request) {
        String header = "";
        if (request.urlPath.contains("ip")) {
            makeIpBody();
            OffsetDateTime currentTime = OffsetDateTime.now();
            header += "HTTP/1.1 200 OK\n"
                + "Date: "
                + currentTime.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\n"
                + "Content-Type: application/json\n"
                + "Content-Length: " + responseBody.length() + "\n"
                + "Connection: keep-alive\n"
                + "Server: gunicorn/19.9.0\n"
                + "Access-Control-Allow-Origin: *\n"
                + "Access-Control-Allow-Credentials: true\n"
                + "\n";
        }
        
        if (request.urlPath.contains("get")) {
            makeGetBody();
            OffsetDateTime currentTime = OffsetDateTime.now();
            header += "HTTP/1.1 200 OK\n"
                + "Date: "
                + currentTime.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\n"
                + "Content-Type: application/json\n"
                + "Content-Length: " + responseBody.length() + "\n"
                + "Connection: keep-alive\n"
                + "Server: gunicorn/19.9.0\n"
                + "Access-Control-Allow-Origin: *\n"
                + "Access-Control-Allow-Credentials: true\n"
                + "\n";
        }
        return header;
    }

    private void makeGetBody() {
        map.put("args", "");
        Map<String, Object> headers = new LinkedHashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Host", inetAddress);
        headers.put("User-Agent", "curl/7.64.1");
        map.put("headers", headers);
        map.put("origin", inetAddress);
        map.put("url", request.getUrlPath());
        String body = "";
        try {
            body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.responseBody = body;
    }

    private void makeIpBody() {
        map.put("origin:", inetAddress);
        String body = "";
        try {
            body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.responseBody = body;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
