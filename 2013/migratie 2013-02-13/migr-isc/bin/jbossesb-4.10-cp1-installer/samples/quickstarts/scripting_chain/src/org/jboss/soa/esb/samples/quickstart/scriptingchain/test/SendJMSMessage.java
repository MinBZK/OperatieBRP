package org.jboss.soa.esb.samples.quickstart.scriptingchain.test;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SendJMSMessage {
    QueueConnection conn;
    QueueSession session;
    Queue que;
    
    
    public void setupConnection() throws JMSException, NamingException
    {
    	InitialContext iniCtx = new InitialContext();
    	Object tmp = iniCtx.lookup("ConnectionFactory");
    	QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
    	conn = qcf.createQueueConnection();
    	que = (Queue) iniCtx.lookup("queue/quickstart_scripting_chain_gw");
    	session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
    	conn.start();
    	System.out.println("Connection Started");
    }
    
    public void stop() throws JMSException 
    { 
        conn.stop();
        session.close();
        conn.close();
    }
    
    public void sendAMessage(String msg) throws JMSException {
    	
        QueueSender send = session.createSender(que);        
        ObjectMessage tm = session.createObjectMessage(msg);
        send.send(tm);        
        send.close();
    }
       
    
    public static void main(String args[]) throws Exception
    {        	    	
    	SendJMSMessage sm = new SendJMSMessage();
    	sm.setupConnection();
    	sm.sendAMessage(args[0]); 
    	sm.stop();
    	
    }
    
}
