package com.example.springwebclientexample;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * This is util class will hit the external server and sends the request to external server and process it
 *  and gets response from the server using jetty Http Client.
 *  Jetty HTTP client is a module to perform HTTP and HTTPS requests.
 *  It can be used to create both asynchronous and synchronous requests
 */

public class ExternalClientLogger {

    Logger logger= LoggerFactory.getLogger(ExternalClientLogger.class);

    /**
     * {@link #enhance(Request)}  takes a Request and gives a request back,
     * allowing us to intercept and log all the pieces we care about.
     */

    public Request enhance(Request inboundRequest) {
        StringBuilder requestBuilder = new StringBuilder();
        /**
         * {@linkplain inboundRequest.onRequestBegin()} It logs the Requests
         */
        inboundRequest.onRequestBegin(request ->
                requestBuilder.append("\n======================================== OUTBOUND REST REQUEST ========================================\n")
                        .append("URI: "+request.getURI())
                        .append("\n")
                        .append("Method: "+request.getMethod()));
        inboundRequest.onRequestHeaders(request -> {
            requestBuilder.append("\nHeaders:");
            for (HttpField header : request.getHeaders()) {
                requestBuilder.append("\t\t" + header.getName() + " : " + header.getValue());
            }
        });
        inboundRequest.onRequestContent((request, content) ->
        {
            try {
                requestBuilder.append("\nBody: \t")
                        .append(new String(content.array(),"UTF-8"))
                        .append("\n=======================================================================================================");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        StringBuilder responseBuilder = new StringBuilder();
        /**
         * {@linkplain inboundRequest.onResponseBegin()} It logs Response
         */
        inboundRequest.onResponseBegin(response ->
                responseBuilder.append("\n======================================== INBOUND REST RESPONSE ========================================\n")
                        .append("Status: "+response.getStatus())
                        .append("\n"));
        inboundRequest.onResponseHeaders(response -> {
            responseBuilder.append("Headers:");
            for (HttpField header : response.getHeaders()) {
                responseBuilder.append("\t" + header.getName() + " : " + header.getValue());
            }
        });
        inboundRequest.onResponseContent(((response, content) -> {
            responseBuilder.append("\nResponse Body: " + StandardCharsets.UTF_8.decode(content).toString())
                    .append("\n=======================================================================================================");
        }));
        requestBuilder.append("\n");
        inboundRequest.onRequestSuccess(request -> logger.info(requestBuilder.toString()));
        inboundRequest.onResponseSuccess(response -> logger.info(responseBuilder.toString()));
        return inboundRequest;
    }
}