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
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package org.jboss.soa.esb.samples.quickstart.httpgateway;

import org.jboss.soa.esb.actions.annotation.BodyParam;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.actions.annotation.PropertyParam;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.lifecycle.annotation.Initialize;
import org.jboss.soa.esb.listeners.ListenerTagNames;
import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.http.HttpRequest;

import com.thoughtworks.xstream.XStream;

public class MyAction {

    private Service service;

    @Initialize
    public void init(ConfigTree config) {
        service = new Service(config.getParent().getAttribute(ListenerTagNames.SERVICE_CATEGORY_NAME_TAG), config.getParent().getAttribute(ListenerTagNames.SERVICE_NAME_TAG));
    }

    @Process
    public String printHttpRequestInfo(@BodyParam String httpPayload, @PropertyParam HttpRequest requestInfo ) {

        System.out.println("&&&&&&&&&&&&&&&& MyAction &&&&&&&&&&&&&&&&&&&&&");
        System.out.println("");
        System.out.println("Service: " + service);
        System.out.println("");
        System.out.println("------------Http Request Info (XStream Encoded)-------------------");
        String requestInfoXML;

        XStream xstream = new XStream();
        requestInfoXML = xstream.toXML(requestInfo);

        System.out.println(requestInfoXML);

        System.out.println("------------Http Request body -------------------");
		System.out.println(httpPayload);

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        StringBuilder response = new StringBuilder();

        response.append("Service: " + service + "\n\n");
        response.append("------------Http Request Info (XStream Encoded)-------------------\n");
        response.append(requestInfoXML);

        return response.toString();
	}
}
