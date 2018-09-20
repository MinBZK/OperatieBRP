/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.kern.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;


public class ServerPasswordCallback implements CallbackHandler
	{
	private final static Map<String, String> map;
	
	static
		{
		Map<String, String> initMap = new HashMap<String, String>();
		initMap.put("serverkey", "serverkeypassword");
		map = Collections.unmodifiableMap(initMap);
		}
	
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
		{
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		pc.setPassword(map.get(pc.getIdentifier()));
		}

	}
