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
package org.jboss.soa.esb.samples.quickstart.publishAsWebservice.test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.jboss.internal.soa.esb.util.StreamUtils;
import org.jboss.soa.esb.ConfigurationException;

public class SOAPTest  {

	public static void main(String args[]) throws ConfigurationException, UnsupportedEncodingException {
 
		final String soap = StreamUtils.getResourceAsString(args[0], "UTF-8");
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				"http://127.0.0.1:8080/Quickstart_publish_as_webservice_inonly/ebws/ESBServiceSample/HelloWorldPubServiceInOnly?wsdl");

		StringRequestEntity requestEntity = new StringRequestEntity(soap);
		postMethod.setRequestEntity(requestEntity);
		try {
			client.executeMethod(postMethod);
			System.out.println("Response from web service (should be empty since this is \"inonly\")");
			System.out.println("[" + postMethod.getResponseBodyAsString() + "]");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);

	}

}
