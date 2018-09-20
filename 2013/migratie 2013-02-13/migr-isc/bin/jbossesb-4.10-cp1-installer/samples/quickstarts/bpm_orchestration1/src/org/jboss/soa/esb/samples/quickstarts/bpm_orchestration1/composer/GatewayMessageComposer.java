/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and others contributors as indicated
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
 */
package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration1.composer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.gateway.LocalFileMessageComposer;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.listeners.message.mime.MimeDecodeException;
import org.jboss.soa.esb.message.Message;

public class GatewayMessageComposer extends LocalFileMessageComposer<File>
{
    private static final String DEFAULT_CHARSET = "UTF-8" ;
    
    private String charset = DEFAULT_CHARSET ;
    
    public void setConfiguration(final ConfigTree config)
        throws ConfigurationException
    {
        super.setConfiguration(config) ;
        charset = config.getAttribute("encoding", DEFAULT_CHARSET) ;
    }
    protected Object getPayload(final File inputFile)
        throws IOException, MimeDecodeException
    {
        final Object contents = super.getPayload(inputFile) ;
        if (contents instanceof byte[])
        {
            final byte[] bytes = (byte[]) contents ;
            return new String(bytes, charset) ;
        }
        else
        {
            return contents ;
        }
    }

    public Object decompose(final Message message, final File inputFile)
        throws MessageDeliverException
    {
        final Object payload = super.decompose(message, inputFile) ;
        if (payload instanceof String)
        {
            final String result = (String)payload ;
            try
            {
                return result.getBytes(charset) ;
            }
            catch (final UnsupportedEncodingException uee)
            {
                throw new MessageDeliverException("Unsupported encoding: " + charset, uee) ;
            }
        }
        else
        {
            return payload ;
        }
    }
}