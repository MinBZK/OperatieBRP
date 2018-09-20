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

package org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise4.handler;

import java.io.PrintStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * This simple SOAPHandler will output the contents of incoming and outgoing
 * messages. Check the MESSAGE_OUTBOUND_PROPERTY in the context to see if this
 * is an outgoing or incoming message. Write a brief message to the print stream
 * and output the message. 
 * 
 * @author Stefano Maestri, stefano.maestri@javalinux.it
 */
public class MyCustomLoggingHandler implements SOAPHandler<SOAPMessageContext> {

    private PrintStream outputStream = System.out;

    /**
     * Default constructor using default System.out PrintStream to print message
     */
    public MyCustomLoggingHandler() {

    }

    /**
     * Constructor for custom PrintStream outputter
     * 
     * @param outStream
     *                the PrintStream to use to print messages.
     */
    public MyCustomLoggingHandler(PrintStream outStream) {
	this.outputStream = outStream;
    }

    public Set<QName> getHeaders() {
	return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
	logToSystemOut(smc);
	return true;
    }

    public boolean handleFault(SOAPMessageContext smc) {
	logToSystemOut(smc);
	return true;
    }

    // nothing to clean up
    public void close(MessageContext messageContext) {
    }

    private void logToSystemOut(SOAPMessageContext smc) {
	Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

	if (outboundProperty.booleanValue()) {
	    outputStream.println("\nMY CUSTOM LOGGING - I got this message:");
	} else {
	    outputStream.println("\nMY CUSTOM LOGGING - I send this message:");
	}
	SOAPMessage message = smc.getMessage();
	try {
	    message.writeTo(outputStream);
	    outputStream.println(""); // just to add a newline
	} catch (Exception e) {
	    outputStream.println("Exception in handler: " + e);
	}
    }

    /**
     * 
     * @param outputStream
     *                custom PrintStream outputter
     */
    public void setOutputStream(PrintStream outputStream) {
	this.outputStream = outputStream;
    }
}
