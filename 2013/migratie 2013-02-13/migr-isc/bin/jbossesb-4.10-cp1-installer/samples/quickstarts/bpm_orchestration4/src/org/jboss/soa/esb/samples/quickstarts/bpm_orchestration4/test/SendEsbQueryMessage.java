/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration4.test;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.jbpm.Constants;
import org.jboss.soa.esb.services.jbpm.cmd.MessageHelper;
import org.jboss.soa.esb.store.Customer;

public class SendEsbQueryMessage {

    public static void main(String args[]) throws Exception {
        if (args[0].trim().equals("")) {
            return;
        }

        System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();

        long processInstanceId = Long.parseLong(args[0]);
        MessageHelper.setLongValue(esbMessage, Constants.PROCESS_INSTANCE_ID, processInstanceId);

        Message response = (new ServiceInvoker("BPM_orchestration4_Starter_Service", "Get_Service_Vars_Sync")).deliverSync(esbMessage, 10000);

        Customer customer = (Customer) response.getBody().get("customer");
        System.out.println("'customer' variable as queried from jBPM process: " + customer);
    }
}