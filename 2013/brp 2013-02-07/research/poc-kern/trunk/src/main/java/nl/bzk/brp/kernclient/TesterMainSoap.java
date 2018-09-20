/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.kernclient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class TesterMainSoap
	{
	public static final String trustStoreFileName = "/home/bhuism/workspace/brp-code/kern/trunk/docs/certificaten/ca.jks";
	public static final String trustStorePassword = "hallo123";
	
	public static final String keyStoreFileName = "/home/bhuism/workspace/brp-code/kern/trunk/docs/certificaten/kern_soap_client.jks";
	public static final String keyStorePassword = "hallo123";
	
	public final static String url = "https://bdev-ap.modernodam.nl/kern/berichtenverwerker/bewerkennationaliteit";
	
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException
		{
//		-Djavax.net.debug=ssl:handshake -Djava.security.debug=certpath

//		System.setProperty("https.protocols", "SSLv3");
//		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");  

		System.setProperty("javax.net.ssl.trustStore", trustStoreFileName);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
		
//		for (int i = 0 ; i < 2 ; i++)
//			{
//			new Tester().startTestThread();
//			};

		
		new TesterMainSoapCaller().call();

		}

	}
