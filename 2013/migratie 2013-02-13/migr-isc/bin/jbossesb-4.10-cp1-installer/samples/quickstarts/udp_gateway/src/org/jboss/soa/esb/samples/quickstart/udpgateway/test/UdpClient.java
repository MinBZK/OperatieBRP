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
package org.jboss.soa.esb.samples.quickstart.udpgateway.test;

import java.io.IOException;
import java.net.UnknownHostException;
import org.apache.commons.net.echo.EchoUDPClient;
import java.net.InetAddress;

public class UdpClient  
{
	public static void main(String... args) 
	{
		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		final String payload = args[2];

		try
		{
			sendUdpString(payload, host, port);
		}
		catch (final UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void sendUdpString(final String payload, final String host, final int port) throws UnknownHostException, IOException
    {
        final EchoUDPClient client = new EchoUDPClient();
        client.open();
        try
        {
            final byte[] writeBuffer = payload.getBytes();
            client.setSoTimeout(3000);
            client.send(writeBuffer, writeBuffer.length, InetAddress.getByName(host), port);
        } 
        finally
        {
            client.close();
        }
    }
}
