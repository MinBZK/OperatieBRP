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
package org.jboss.soa.esb.samples.quickstart.customaction;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.BeanConfiguredAction;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.actions.ActionProcessingException;

public class CustomBeanConfigAction extends AbstractActionLifecycle
  implements BeanConfiguredAction
{
    
    private String information;

	private Integer repeatCount;

	private String serviceCategory;

	private String serviceName;

	public void setInformation(String information) {
           this.information = information;
	}

	public void setRepeatCount(Integer repeatCount) {
           this.repeatCount = repeatCount;
	}
	
	public Message process(Message message) throws ActionProcessingException {
	  System.out.println("[" + serviceCategory + ":" + serviceName + "] Repeat message: " + information + " " + repeatCount + " times:");
      for (int i=0; i < repeatCount; i++) {
          System.out.println(information);
      } 
      return message;
	}	

	public void setServiceCategory(final String serviceCategory)
    {
		this.serviceCategory = serviceCategory;
	}

	public void setServiceName(final String serviceName)
    {
		this.serviceName = serviceName;
	}
		
}
