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
package org.jboss.soa.esb.samples.quickstart.jmstopic;

import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;

/**
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class MessageAugmentor extends AbstractActionPipelineProcessor {

    private String addition;
    private Service targetService;

    public MessageAugmentor(ConfigTree config) throws ConfigurationException {
        addition = config.getRequiredAttribute("addition");
        String target = config.getRequiredAttribute("target");
        String[] targetTokens = target.split(":");

        if(targetTokens.length != 2) {
            throw new ConfigurationException("Action not configured properly - 'target' service property must be in format 'category:name'.");
        }

        targetService = new Service(targetTokens[0], targetTokens[1]);
    }

    public Message process(final Message message) throws ActionProcessingException {
        message.getProperties().setProperty("addition", addition);

        System.out.println("Received message on Topic.  Sending '" + addition + "' to " + targetService);

        try {
            getInvoker().deliverAsync(message);
        } catch (MessageDeliverException e) {
            throw new ActionProcessingException("Failed to deliver message: " + e.getMessage());
        }

        return message;
    }

    private ServiceInvoker getInvoker() throws ActionProcessingException {
        try {
            return new ServiceInvoker(targetService);
        } catch (MessageDeliverException e) {
            throw new ActionProcessingException("Failed to create ServiceInvoker: " + e.getMessage());
        }
    }
}
