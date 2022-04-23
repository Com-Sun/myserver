package com.nhnacademy.myserver.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Request {
    Map<String, String> requestHeaderMap = new LinkedHashMap<>();
    String method;
    String urlPath;
    String urlPathArgs="";
    String requestBody;

    public Request(String[] httpRequest) {
        String[] requestHeaderToLine = httpRequest[0].split("\n");
        for (String s : requestHeaderToLine) {
            if(s.contains("GET") || s.contains("POST")) {
                String[] temp = s.split(" ");
                method = temp[0];
                if (temp[1].contains("?")) {
                    urlPath = temp[1];
                    urlPathArgs = temp[1].split("\\?")[1];
                } else {
                    urlPath = temp[1];
                }
                continue;
            }
            String[] temp = s.split(":");
            requestHeaderMap.put(temp[0], temp[1]);
            if(Objects.equals(method, "POST")) {
                requestBody = httpRequest[1];
            }

        }
    }

    public String getRequestHeader(String target) {
        return requestHeaderMap.get(target);
    }

    public String getMethod() {
        return method;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getUrlPathArgs() {
        return urlPathArgs;
    }

    public String getRequestBody() {
        return requestBody;
    }
}
