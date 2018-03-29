/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie;

import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath:initielevulling.xml", "classpath:mock-jms.xml", "classpath:mock-datasource.xml", "classpath:/test-dal-beans.xml"}, initializers = AbstractInitieleVullingIT.PortInitializer.class)
public abstract class AbstractInitieleVullingIT extends AbstractIT {

    /**
     * Dynamisch poorten voor resources bepalen.
     */
    public static final class PortInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(final ConfigurableApplicationContext applicationContext) {
            final Properties properties = new Properties();
            properties.setProperty("synchronisatie.jmx.serialize.port", "0");

            applicationContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("ports", properties));
        }
    }
}
