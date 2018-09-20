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
package org.jboss.soa.esb.samples.quickstart.aggregator;

import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Attachment;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.util.Util;

import java.io.Serializable;

/**
 * Assemble an aggregated message.
 * <p/>
 * This is just an example.  Of course a real worl example would do something more
 * useful than just append the messages together in a StringBuffer!
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class AggregatedMessageAssembler extends AbstractActionPipelineProcessor {

    public AggregatedMessageAssembler(ConfigTree config) {
    }

    public Message process(Message message) throws ActionProcessingException {
        Attachment attachments = message.getAttachment();
        int attachmentCount = attachments.getUnnamedCount();
        StringBuffer assemblyBuffer = new StringBuffer();

        for (int i = 0; i < attachmentCount; i++) {
            try {
                Message aggrMessage = Util.deserialize((Serializable) attachments.itemAt(i));
                String payload = aggrMessage.getBody().get().toString();
                
                assemblyBuffer.append("**** Payload from Message Attachment " + i + ":\n");
                assemblyBuffer.append(payload + "\n");
            } catch (Exception e) {
                // Not an aggregated message attachment... continue...
            }
        }

        message.getBody().add(assemblyBuffer.toString());

        return message;
    }
}
