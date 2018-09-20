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
 * (C) 2005-2006, JBoss Inc.
 */

import javax.jms.JMSException
import javax.jms.ObjectMessage
import javax.jms.Queue
import javax.jms.QueueConnection
import javax.jms.QueueConnectionFactory
import javax.jms.QueueSender
import javax.jms.QueueSession
import javax.naming.Context
import javax.naming.InitialContext
import javax.naming.NamingException

import org.jboss.soa.esb.actions.StoreMessageToFile

/**
 * Simple Groovy load script that will simulate load for a JMS gateway based ESB service call.
 *
 * @author <a href="mailto:james.williams@redhat.com">james.williams@redhat.com</a>
 */
 
class LoadAgentBean
{
	def msgCount
	def batchInterval
	def payload
	def queue	
}

class LoadGenerator
{
	def batchCount
	def agentBeanList
}

class JmsLoadAgent 
{
	def loadGenerator
	boolean debug = false
	
	JmsLoadAgent(aLoadConfigFile)
	{
		initLoadGenerator(aLoadConfigFile)
	}
	
	JmsLoadAgent(LoadGenerator aLoadGenerator)
	{
		loadGenerator = aLoadGenerator
	}
	
	private generateLoad() 
	{	
		def batchNumber = 0;
		1.upto(loadGenerator.batchCount) 
		{ 
			loadGenerator.agentBeanList.each
			{
				agentBean ->			
				sendMessages(agentBean)
				if (debug)
				{
					println "$agentBean.msgCount messages have been delivered to: $agentBean.queue"	
				}							
			}	
			batchNumber++
			if (debug)
			{	
				println "batch number $batchNumber of $loadGenerator.batchCount has been processed"
			}
		}			
	}
	
	private initLoadGenerator(loadConfigFile)
	{
		def loadScript = new XmlParser().parse(new File(loadConfigFile))
		loadGenerator = new LoadGenerator()
		loadGenerator.agentBeanList = new ArrayList()
		loadGenerator.batchCount = loadScript.'load-generators'.'@batch-count'[0].toInteger()
		loadScript.'load-generators'.'load-generator'.each
		{
			loadGeneratorEntry ->			
			def agentBean = new LoadAgentBean()
			agentBean.msgCount = loadGeneratorEntry.'@msg-count'.toLong()
			agentBean.batchInterval = loadGeneratorEntry.'@batch-interval-seconds'.toInteger()
			agentBean.payload = loadGeneratorEntry.'@payload'
			agentBean.queue = loadGeneratorEntry.'@queue'
			loadGenerator.agentBeanList << agentBean									
		}	
	}
	
	private sendMessages(agentBean)
	{		
		QueueConnection conn
    	QueueSession session
    	Queue que
		Properties properties1 = new Properties()
		properties1.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory")
		properties1.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces")
		properties1.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1099")
		InitialContext iniCtx = new InitialContext(properties1)
		
		Object tmp = iniCtx.lookup("ConnectionFactory")
		QueueConnectionFactory qcf = (QueueConnectionFactory) tmp
		conn = qcf.createQueueConnection()
		que = (Queue) iniCtx.lookup(agentBean.queue)
		session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE)
		conn.start()				
		
		QueueSender send = session.createSender(que)    
		ObjectMessage tm = session.createObjectMessage(new File(agentBean.payload).text)    
		tm.setStringProperty(StoreMessageToFile.PROPERTY_JBESB_FILENAME, "LoadGeneratorTest.log");
		1.upto(agentBean.msgCount) 
		{ 
		   send.send(tm)   
		}     
		   
		send.close() 
		sleep(agentBean.batchInterval*1000)
						
		conn.stop();
	    session.close();
	    conn.close();
	    
	}	
	
   static void main(args)
   {
		println "Starting JMS Load Agent as a daemon thread"
		Thread.startDaemon {
			JmsLoadAgent agent = new JmsLoadAgent(args[0])
			agent.generateLoad()
		}		
   }
}





