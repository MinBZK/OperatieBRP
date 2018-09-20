/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DataTaalContextLader {

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:datataal-context.xml");

    public void zetContext() {
        SpringBeanProvider.setContext(applicationContext);
    }
}
