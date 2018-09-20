package org.jboss.soa.esb.servlet;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.soa.esb.common.Environment;
import org.jboss.soa.esb.listeners.config.ConfigurationController;

/**
 * Context listener used to configure the ESB in a war environment.
 * @author kevin
 */
public class ConfigListener implements ServletContextListener
{
    /**
     * The configuration controller.
     */
    private ConfigurationController configurationController ;
    
    /**
     * Initialise the ESB.
     * @param servletContextEvent The initialised context event.
     */
    public void contextInitialized(final ServletContextEvent servletContextEvent)
    {
        final ServletContext servletContext = servletContextEvent.getServletContext() ;
        
        servletContext.log("Initialising ESB Configuration Controller") ;
        final String catalinaHome = System.getProperty("catalina.home") ;
        if (catalinaHome == null)
        {
            servletContext.log("***** Error initialising context, catalina home not set") ;
        }
        else
        {
            final File catalinaHomeDir = new File(catalinaHome) ;
            final File confDir = new File(catalinaHomeDir, "conf") ;
            
            final File propertyFile = new File(confDir, "jbossesb-properties.xml") ;
            final String absPropertyFile = propertyFile.getAbsolutePath() ;
            
            System.setProperty(Environment.PROPERTIES_FILE, "abs://" + absPropertyFile) ;
            
            servletContext.log("ESB Property configuration obtained from file: " + absPropertyFile);
            
            final File juddiPropertyFile = new File(confDir, "juddi.properties") ;
            final String absJuddiPropertyFile = juddiPropertyFile.getAbsolutePath() ;
            System.setProperty("juddi.propertiesFile", absJuddiPropertyFile) ;
            
            servletContext.log("ESB juddi configuration obtained from file: " + absJuddiPropertyFile) ;
            
            final File configFile = new File(confDir, "jboss-esb.xml") ;
            final String absConfigFile = configFile.getAbsolutePath() ;
            
            servletContext.log("ESB configuration obtained from file: " + absConfigFile) ;
            
            configurationController = new ConfigurationController(absConfigFile, null) ;
            new Thread(configurationController).start() ;
        }
    }

    /**
     * Terminate the ESB.
     * @param servletContextEvent The destroyed context event.
     */
    public void contextDestroyed(final ServletContextEvent servletContextEvent)
    {
        if (configurationController != null)
        {
            final ServletContext servletContext = servletContextEvent.getServletContext() ;
            
            servletContext.log("ESB starting termination") ;
            
            configurationController.requestEnd() ;
            configurationController.waitUntilEnded() ;
            
            servletContext.log("ESB terminated") ;
        }
    }
}
