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
package org.jboss.soa.esb.samples.https.server;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;

/**
 * @autour <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class HttpRequestPrinter extends AbstractActionLifecycle {

    protected ConfigTree _config;

    public HttpRequestPrinter(ConfigTree config) {
        _config = config;
    }


    public Message process(Message message) throws Exception {
        Properties properties = message.getProperties();

        System.out.println("=========== Server Request: ====================================");
        System.out.println("Message Payload:");
        System.out.println("\t[" + message.getBody().get() + "]");

        System.out.println();
        System.out.println("\tHeaders:");
        System.out.println("\t\thost: " + properties.getProperty("host"));
        System.out.println("\t\tMethod: " + properties.getProperty("MethodType"));
        System.out.println("\t\tPath: " + properties.getProperty("Path"));
        System.out.println("\t\tuser-agent: " + properties.getProperty("user-agent"));
        System.out.println("\t\tcontent-type: " + properties.getProperty("content-type"));
        System.out.println("================================================================");

        message.getBody().add("Http Response Payload!!");

        return message;
    }
}