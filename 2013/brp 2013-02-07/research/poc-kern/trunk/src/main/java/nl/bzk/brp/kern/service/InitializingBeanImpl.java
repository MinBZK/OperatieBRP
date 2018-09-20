/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.kern.service;

import java.util.Enumeration;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InitializingBeanImpl implements InitializingBean
	{
	@Inject
	private Log log;
	
	@Inject
	private VersionService versionService;
	
	private void logSystemProperties()
		{
		@SuppressWarnings("unchecked")
		Enumeration<String> propertyNames = (Enumeration<String>) System.getProperties().propertyNames();
		while (propertyNames.hasMoreElements())
			{
			String propertyName = propertyNames.nextElement();
			log.debug(propertyName + "=" + System.getProperty(propertyName));
			}
		}
	
	@Override
	public void afterPropertiesSet()
		{
		log.info(versionService.getApplicationString());
		//logSystemProperties();
		}
	}
