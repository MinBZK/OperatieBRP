import javax.management.*
import javax.naming.Context
import javax.naming.InitialContext
import javax.naming.NamingException

/**
 * Simple Groovy load script that will provide TPS statistics for a ESB service call and JMS Queues.
 *
 * @author <a href="mailto:james.williams@redhat.com">james.williams@redhat.com</a>
 */
 
class ServiceMetricsBean 
{
	def tps
	def tpsAvg
	def duration	
}

class QueueMetricsBean 
{
	def queueFactor
	def queueFactorAvg
	def queueFactorTotal
	def duration
}

class ReporterBean
{
	def fastestQueue
	def tpsIntervalSeconds
}

class JmsReporterBean extends ReporterBean
{
	def queue
}

class ServiceReporterBean extends ReporterBean
{
	def serviceCategory
	def serviceName
	def esbArchiveName	
}

class LoadReport
{
	LoadUtil util
	def loadConfigFile
	InitialContext ctx
	MBeanServerConnection server
	def svcStatsMap = [:]
	def qStatsMap = [:]
	def svcReportLog
	def queueReportLog
		
	LoadReport(aLoadConfigFile, user, password)
	{
		loadConfigFile = aLoadConfigFile
		ctx = new InitialContext(); // From jndi.properties
		server = (MBeanServerConnection) ctx.lookup("jmx/invoker/RMIAdaptor")
		util = new LoadUtil(user, password)
		println "USER=$user PASSWORD=$password"
	}

   public generateMetrics()
   {
   		def loadScript = new XmlParser().parse(new File(loadConfigFile))
   		createReportFiles(loadScript.'@log-directory')
		boolean done = false
		def startTime = new Date().getTime()
		def qAggregateMap = [:]
		def svcAggregateMap = [:]
		def times = 0
		while(!done)
		{					
			times++	
			
			loadScript.'reporters'.'jms-reporters'.'jms-reporter'.each
			{
				jmsReporter ->
				QueueMetricsBean qBean = new QueueMetricsBean()
				JmsReporterBean reportBean = new JmsReporterBean()
				reportBean.queue = jmsReporter.'@queue-mbean-name'
				qBean.queueFactor = util.getQueueDepthFactor(reportBean.queue) / 100					
								
				if (times == 1)
				{
					qAggregateMap."$reportBean.queue-FactorTotal" = 0					
				}
				
				
				qAggregateMap."$reportBean.queue-FactorTotal" += qBean.queueFactor 
				qAggregateMap."$reportBean.queue-FactorAvg" =  qAggregateMap."$reportBean.queue-FactorTotal" / times
				
				qBean.queueFactorTotal =  qAggregateMap."$reportBean.queue-FactorTotal" 
				qBean.queueFactorAvg = qAggregateMap."$reportBean.queue-FactorAvg"
				qBean.duration = ( new Date().getTime() - startTime ) / 1000 / 60
				
				qStatsMap[reportBean.queue] = qBean								
			}
			
			loadScript.'reporters'.'service-reporters'.'service-reporter'.each
			{
				serviceReporter ->	
				ServiceMetricsBean svcMetricsBean = new ServiceMetricsBean()
				ServiceReporterBean svcReportBean = new ServiceReporterBean()
				svcReportBean.serviceCategory = serviceReporter.'@service-category'
				svcReportBean.serviceName = serviceReporter.'@service-name'
				svcReportBean.esbArchiveName =  serviceReporter.'@esb-archive-name'
				
				if (times == 1)
				{
					svcAggregateMap."$svcReportBean.serviceName-TPSTotal" = 0
					svcAggregateMap."$svcReportBean.serviceName-TPSAvg" = 0
				}
				
				svcMetricsBean.tps = util.getEsbServiceTps(svcReportBean)							
				svcAggregateMap."$svcReportBean.serviceName-TPSTotal" += svcMetricsBean.tps	
				svcAggregateMap."$svcReportBean.serviceName-TPSAvg" =  svcAggregateMap."$svcReportBean.serviceName-TPSTotal" / times
				
				svcMetricsBean.tpsAvg =  svcAggregateMap."$svcReportBean.serviceName-TPSAvg"
				svcMetricsBean.duration = ( new Date().getTime() - startTime ) / 1000 / 60
				
				svcStatsMap[svcReportBean.serviceName] = svcMetricsBean				
			}	
			
			printMetrics(svcStatsMap,qStatsMap)	
			csvLogMetrics(svcStatsMap,qStatsMap)	
			def fastestQueue = loadScript.'reporters'.'jms-reporters'.'@fastest-queue-mbean-name'[0]
			done = util.fastestQueueDone("$fastestQueue")			
		}
   }
   
   private createReportFiles(logDir)
   {
   		svcReportLog = new File(logDir + "/load-service-report-" + new Date().getTime() + ".csv")
   		svcReportLog.getParentFile().mkdirs()
		svcReportLog.append("Service, TPS, AVG TPS, Duration\n")
		
		queueReportLog = new File(logDir + "/load-queue-report-" + new Date().getTime() + ".csv")
   		queueReportLog.getParentFile().mkdirs()
		queueReportLog.append("Queue, Factor, Factor AVG, Duration\n")
   }
   
   private printMetrics(svcStatsMap,qStatsMap)
   {
   		println "######Service Stats######"
   		svcStatsMap.each {
   			key, svcStats ->	
   			println "Svc: $key, TPS: $svcStats.tps, AVG TPS: $svcStats.tpsAvg, Duration: $svcStats.duration" 
   		}
   		println "######Queue Stats######"
   		qStatsMap.each {
   			key, qStats ->	
   			println "Queue: $key, Factor: $qStats.queueFactor, Factor AVG: $qStats.queueFactorAvg, Duration: $qStats.duration" 
   		}
   }
   
   private csvLogMetrics(serviceMetricsBean,queueMetricsBean)
   {
   		svcStatsMap.each {
   			key, svcStats ->	
   			svcReportLog.append("$key, $svcStats.tps, $svcStats.tpsAvg, $svcStats.duration\n") 
   		}
   		
   		qStatsMap.each {
   			key, qStats ->	
   			def queueKeys = key.split(',')
   			def queueName = queueKeys[1]
   			queueReportLog.append("$queueName, $qStats.queueFactor, $qStats.queueFactorAvg, $qStats.duration\n")
   		}
   }  

	static void main(args)
	{
		LoadReport reporter = null
		if (args.size() == 3) {
			 reporter = new LoadReport(args[0], args[1], args[2])
		} else {
			 reporter = new LoadReport(args[0], null, null)
		}
		reporter.generateMetrics()	
	}

}
 

    
   
