import javax.management.*
import javax.naming.Context
import javax.naming.InitialContext
import javax.naming.NamingException

import org.jboss.soa.esb.samples.quickstart.jmx.*;

/**
 * Simple Groovy load script that will provide TPS statistics for a ESB service call and JMS Queues.
 *
 * @author <a href="mailto:james.williams@redhat.com">james.williams@redhat.com</a>
 */
 
class LoadUtil
{
   InitialContext ctx
   MBeanServerConnection server	
   String user
   String password
   
   public LoadUtil()
   {
	user = null
	password = null
   }

   public LoadUtil(f_user, f_password)
   {
	user = f_user
	password = f_password
   }

   public Long getLongAttribute (ObjectName on, String an) {
        Long val = null
		JMXAttributeFinder jf = new JMXAttributeFinder(user, password)
		val = (Long) jf.query(on, an)
		return val
   }

   public Integer getIntegerAttribute (ObjectName on, String an) {
		Integer val = null
		JMXAttributeFinder jf = new JMXAttributeFinder(user, password)
		val = (Integer) jf.query(on, an)
   }

   public float getEsbServiceTps(serviceReportBean)
   {
   		def serviceMessageCount = {
			getLongAttribute(new ObjectName("jboss.esb:service-category=$serviceReportBean.serviceCategory,service-name=$serviceReportBean.serviceName,deployment=$serviceReportBean.esbArchiveName,category=MessageCounter"), "overall service message count")
   		}
   		
   		def processedMsgsBefore = serviceMessageCount()
 		sleep(1000)
 		def processedMsgsAfter = serviceMessageCount() 	
 		
 		return processedMsgsAfter - processedMsgsBefore	
   }
   
   public boolean fastestQueueDone(queue)
   {
   		if (getQueueDepthFactor(queue) == 0)
   		{
   			return true
   		}
   		else
   		{
   			return false
   		}
   }
   
   public double getQueueDepthFactor(queue)
   {
   		def queueDepthCount = {
			def temp = 0
			try {
				temp = getLongAttribute(new ObjectName("$queue"), "QueueDepth")
			} catch (javax.management.AttributeNotFoundException e) {
				temp = getIntegerAttribute(new ObjectName("$queue"), "MessageCount")
			}
			if (temp == null) {
				return 0
			} else {
				return temp
			}
   		}
		
   		def depthBefore = queueDepthCount()
 		sleep(1000)
 		def depthAfter = queueDepthCount() 	
 		
 		return depthBefore - depthAfter
   }   
   
}
 

    
   
