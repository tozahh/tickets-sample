package com.it4pro.ticketservice.tickets.features.utils;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RequestToCurlFormatter {

    public static String format(Request request) {

        String curlOptions = "";

        boolean compressed = false;

        StringBuilder curlCmd = new StringBuilder("curl");
        curlCmd.append(" ").append(curlOptions);
        curlCmd.append(" -X ").append(request.method());

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            if ("Accept-Encoding".equalsIgnoreCase(name) && "gzip".equalsIgnoreCase(value)) {
                compressed = true;
            }
            curlCmd.append(" -H " + "'").append(name).append(": ").append(value).append("'");
        }

        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            curlCmd.append(" --data $'").append(buffer.readString(charset)).append("'");
        }

        curlCmd.append((compressed) ? " --compressed " : " ").append(request.url());

        return curlCmd.toString();
    }
}
