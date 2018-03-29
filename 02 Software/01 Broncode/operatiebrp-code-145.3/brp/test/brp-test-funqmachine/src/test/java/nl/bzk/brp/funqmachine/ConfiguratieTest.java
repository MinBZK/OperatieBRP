/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine;

import static org.junit.Assert.assertNotNull;

import java.security.SecureRandom;
import java.util.Properties;
import nl.bzk.brp.bijhouding.business.BijhoudingService;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Verifieert of de configuraties werken
 */
public class ConfiguratieTest {

    @Test
    public void testFunqmachineConfig() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/funqmachine-beans.xml");
        assertNotNull(context.getBean("masterDataSource"));
        context.close();
    }

    @Test
    public void testConversieConfig() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/funqmachine-beans.xml");

        final ClassPathXmlApplicationContext context2 = new ClassPathXmlApplicationContext();
        context2.setConfigLocation("config/funqmachine-conversie-beans.xml");
        context2.setParent(context);

        final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.put("atomikos_unique_name", String.valueOf((int) (Math.random() * Integer.MAX_VALUE)));
        propConfig.setProperties(properties);
        context2.addBeanFactoryPostProcessor(propConfig);

        context2.refresh();

        assertNotNull(context2.getBean(ConverteerLo3NaarBrpService.class));
        context2.close();
        context.close();
    }

    @Test
    @Ignore("Test leidt tot OutOfMemory exception")
    public void testBijhoudingConfig() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/funqmachine-beans.xml");
        context.getBean("masterDataSource");

        final ClassPathXmlApplicationContext context2 = new ClassPathXmlApplicationContext();
        context2.setConfigLocation("config/funqmachine-bijhouding-beans.xml");
        context2.setParent(context);

        final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.put("atomikos_unique_name", String.valueOf(new SecureRandom().nextInt() * Integer.MAX_VALUE));
        propConfig.setProperties(properties);
        context2.addBeanFactoryPostProcessor(propConfig);

        context2.refresh();

        assertNotNull(context2.getBean(BijhoudingService.class));
        context2.close();
        context.close();
    }
}
