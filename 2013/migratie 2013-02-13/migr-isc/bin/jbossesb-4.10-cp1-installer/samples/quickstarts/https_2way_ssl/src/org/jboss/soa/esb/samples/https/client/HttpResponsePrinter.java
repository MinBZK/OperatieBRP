package org.jboss.soa.esb.samples.https.client;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpHeader;
import org.jboss.soa.esb.http.HttpResponse;
import org.jboss.soa.esb.message.Message;

import java.util.List;

public class HttpResponsePrinter extends AbstractActionLifecycle {

    protected ConfigTree _config;

    public HttpResponsePrinter(ConfigTree config) {
        _config = config;
    }


    public Message process(Message message) throws Exception {

        HttpResponse httpResponse = (HttpResponse) message.getBody().get(HttpResponse.RESPONSE_KEY);

        System.out.println("=========== Client Response: ===================================");
        System.out.println("Message Payload:");
        System.out.println("\t[" + message.getBody().get() + "]");

        System.out.println();
        System.out.println("Http Response:");
        System.out.println("\tCode: " + httpResponse.getResponseCode());
        System.out.println("\tLength: " + httpResponse.getLength());
        System.out.println("\tEncoding: " + httpResponse.getEncoding());

        System.out.println("\tHeaders:");
        List<HttpHeader> headers = httpResponse.getHttpHeaders();
        for (HttpHeader header : headers) {
            System.out.println("\t\t" + header.getName() + ": " + header.getValue());
        }

        System.out.println("================================================================");

        return message;
    }


}