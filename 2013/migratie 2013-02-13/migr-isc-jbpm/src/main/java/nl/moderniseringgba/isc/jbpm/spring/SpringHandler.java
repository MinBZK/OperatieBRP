/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Abstracte handler om iets te doen in spring. Deze handler zorgt voor het opzetten van de application context
 * (bpm-application-context.xml).
 */
public class SpringHandler {

    private static ApplicationContext applicationContext;

    /**
     * Zoek de bean met een bepaalde naam en klasse.
     * 
     * @param <T>
     *            klasse
     * @param name
     *            naam
     * @param clazz
     *            klasse
     * @return bean
     */
    protected final <T> T getBean(final String name, final Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    private ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext("bpm-application-context.xml");
        }

        return applicationContext;
    }
}
