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
package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration2.test;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SendJMSMessageDeploy {
    QueueConnection conn;
    QueueSession session;
    Queue que;
    
    
    
    public void setupConnection() throws JMSException, NamingException
    {
        Properties properties1 = new Properties();
        properties1.put(Context.INITIAL_CONTEXT_FACTORY,
        "org.jnp.interfaces.NamingContextFactory");
        properties1.put(Context.URL_PKG_PREFIXES,
        "org.jboss.naming:org.jnp.interfaces");
        properties1.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1099");
        InitialContext iniCtx = new InitialContext(properties1);

        Object tmp = iniCtx.lookup("ConnectionFactory");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();     
        que = (Queue) iniCtx.lookup("queue/quickstart_bpm_orchestration2_deploy_Request_gw");
        session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
        // System.out.println("Connection Started: quickstart_bpm_orchestration2_deploy_Request_gw");
    }
    
    public void stop() throws JMSException 
    { 
        try {
            if(conn != null) {
                conn.stop();
            }
        } finally {
            try {
                if(session != null) {
                    session.close();
                }
            } finally {
                if(conn != null) {
                    conn.close();
                }
            }
        }
    }
    
    public void sendAMessage(String msg) throws JMSException {
      
        QueueSender send = session.createSender(que);        
        ObjectMessage tm = session.createObjectMessage(msg);
        send.send(tm);        
        send.close();
    }
       
    
    public static void main(String args[]) throws Exception
    {               
      SendJMSMessageDeploy sm = new SendJMSMessageDeploy();
        try {
            sm.setupConnection();
            sm.sendAMessage(args[0]);
        } catch (Exception e) {
            System.out.println("\n**** Failed to send message to JMS Destination '" + args[0] + "'.  " +
                               "\n**** Your JMS Provider (e.g. your JBoss ESB/App Server) may not be running, or the JMS Destination may not be deployed." +
                               "\n**** Exception: " + e.getMessage());
        } finally {
            sm.stop();
        }      
    }
    
}
