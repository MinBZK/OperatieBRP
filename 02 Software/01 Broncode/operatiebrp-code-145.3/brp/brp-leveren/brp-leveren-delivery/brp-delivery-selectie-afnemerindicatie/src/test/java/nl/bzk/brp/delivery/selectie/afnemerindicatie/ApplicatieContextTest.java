/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.afnemerindicatie;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.HashMap;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.delivery.dataaccess.EmbeddedDatabaseConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.test.context.ContextConfiguration;

/**
 */
@ContextConfiguration(classes = ApplicatieContextTest.SelectieAfnemerindicatieTestConfiguratie.class)
public class ApplicatieContextTest extends AbstractDataAccessTest {

    @Test
    public void test() {
    }

    @Configuration
    @Import(EmbeddedDatabaseConfiguration.class)
    @ImportResource(value =  {"classpath:selectie-afnemerindicatie-delivery-beans.xml"} )
    @PropertySource(value = "selectieafnemerindicatie", factory = SelectieAfnemerindicatiePropertySourceFactory.class)
    public static class SelectieAfnemerindicatieTestConfiguratie {
    }

    static class SelectieAfnemerindicatiePropertySourceFactory implements PropertySourceFactory {

        @Override
        public org.springframework.core.env.PropertySource<?> createPropertySource(final String s,
                                                                                   final EncodedResource encodedResource) throws IOException {
            final HashMap<String, Object> source = Maps.newHashMap();
            source.put("brp.selectie.messagebroker.jms.client.url", "tcp://0.0.0.0:61619");
            source.put("brp.selectie.verwerker.jms.concurrency", "1");
            source.put("brp.selectie.schrijver.resultaatfolder", "/a/b/c");
            source.put("brp.selectie.verwerker.selectiebestandenfolder", "/d/e/f");
            return new MapPropertySource("selectieverwerker", source);
        }
    }
}
