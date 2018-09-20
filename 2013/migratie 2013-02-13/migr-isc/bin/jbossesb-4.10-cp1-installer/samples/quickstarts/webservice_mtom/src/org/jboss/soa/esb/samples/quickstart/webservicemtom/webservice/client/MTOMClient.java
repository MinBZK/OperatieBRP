/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * (C) 2005-2006, JBoss Inc.
 */
package org.jboss.soa.esb.samples.quickstart.webservicemtom.webservice.client;

import org.jboss.soa.esb.samples.quickstart.webservicemtom.webservice.*;
import org.jboss.internal.soa.esb.util.StreamUtils;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.SOAPBinding;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;

import sun.awt.image.ToolkitImage;

/**
 * MTOM Client.
 * <p/>
 * The basis of this code was lifted from JBossWS test code.  Thanks guys :-)
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class MTOMClient {
    
    public static void main(String[] args) throws MalformedURLException {
        MTOMEndpoint mtomEndpoint;

        // Get the client endpoint port...
        mtomEndpoint = getEndpointPort();

        // Create the Image request and send it to the MTOM endpoint...
        byte[] imageBytes = StreamUtils.readStream(MTOMClient.class.getResourceAsStream("jboss.png"));
        Image img = Toolkit.getDefaultToolkit().createImage(imageBytes);
        if (img == null) {
            throw new IllegalStateException("Failed to load the 'jboss.gif' image from the classpath!");
        }
        ImageRequest request = new ImageRequest();
        request.setData(img);
        ImageResponse response = mtomEndpoint.echoImage(request);

        // Check the response...
        assertImagesEqual(request, response);
    }

    private static MTOMEndpoint getEndpointPort() throws MalformedURLException {
        MTOMEndpoint mtomEndpoint;
        QName serviceName = new QName("http://webservice.webservicemtom.quickstart.samples.esb.soa.jboss.org/", "MTOMService");
        URL wsdlURL = new URL("http://localhost:8080/contract/contract.jsp?serviceCat=MyServiceCategory&serviceName=MyMTOMService&protocol=http");
        //URL wsdlURL = new URL("http://127.0.0.1:8080/Quickstart_webservice_mtom/MTOMService?wsdl");

        Service service = Service.create(wsdlURL, serviceName);
        mtomEndpoint = service.getPort(MTOMEndpoint.class);

        // enable MTOM
        SOAPBinding binding = (SOAPBinding)((BindingProvider)mtomEndpoint).getBinding();
        binding.setMTOMEnabled(true);

        List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.addAll(binding.getHandlerChain());
        handlerChain.add(new MTOMProtocolHandler());
        binding.setHandlerChain(handlerChain);
        return mtomEndpoint;
    }

    private static void assertImagesEqual(ImageRequest request, ImageResponse response) {
        ToolkitImage reqImage = (ToolkitImage) request.getData();
        BufferedImage resImage = (BufferedImage) response.getData();

        assertEquals("Image heights don't match.", reqImage.getHeight(), resImage.getHeight());
        assertEquals("Image widths don't match.", reqImage.getWidth(), resImage.getWidth());

        System.out.println("Successfully sent image data (binary) to MTOM Service endpoint, which echoed it back!");
    }

    private static void assertEquals(String failureMessage, Object obj1, Object obj2) {
        if(!obj1.equals(obj2)) {
            throw new RuntimeException(failureMessage + "  Expected '" + obj1 + "', actual '" + obj2 + "'.");
        }
    }
}
