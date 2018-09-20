/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.soa.esb.samples.quickstart.webservicemtom.webservice;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.awt.*;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

@WebService(name = "MTOMEndpoint", serviceName = "MTOMService", endpointInterface = "org.jboss.soa.esb.samples.quickstart.webservicemtom.webservice.MTOMEndpoint")
public class MTOMEndpointBean implements MTOMEndpoint {

    public ImageResponse echoImage(ImageRequest request) {
        Image image = request.getData();
        System.out.println("*** Received Image: " + image);
        //System.out.println("*** Image attached to ESB Message: " + image);
        
        return new ImageResponse(image);
    }
}
