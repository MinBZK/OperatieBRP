/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.kernclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.security.Security;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TesterMainHtml
	{
	private static final String server = "bdev-ap.modernodam.nl";
	
	public static void main(String[] args) throws UnknownHostException, IOException
		{
		System.setProperty("javax.net.ssl.keyStore", "/home/bhuism/workspace/brp-code/kern/trunk/docs/certificaten/kern_soap_client.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "hallo123");
		
		System.setProperty("javax.net.ssl.trustStore", "/home/bhuism/workspace/brp-code/kern/trunk/docs/certificaten/ca.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "hallo123");

		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = (SSLSocket) factory.createSocket(server, 443);

		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		out.println("GET /kern/ HTTP/1.1");
		out.println("Host: " + server);
		
		out.println();
		out.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String line;

		while ((line = in.readLine()) != null)
			{
			System.out.println(line);
			}

		out.close();
		in.close();
		}

	}
