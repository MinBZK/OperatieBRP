/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending.it;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.HashMap;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.delivery.dataaccess.EmbeddedDatabaseConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = ApplicatieContextTest.VerzendingTestConfiguration.class)
public class ApplicatieContextTest extends AbstractDataAccessTest {

    @Test
    public void test() throws IOException {
        Assert.assertTrue(true);
    }


    @Import(EmbeddedDatabaseConfiguration.class)
    @ImportResource(value =  {"classpath:brp-verzending-context.xml"} )
    @PropertySource(value = "verzending", factory = VerzendingPropertySourceFactory.class)
    public static class VerzendingTestConfiguration {

    }

    static class VerzendingPropertySourceFactory implements PropertySourceFactory {

        @Override
        public org.springframework.core.env.PropertySource<?> createPropertySource(final String s,
                                                                                   final EncodedResource encodedResource) throws IOException {
            final HashMap<String, Object> source = Maps.newHashMap();
            source.put("security.keystore.keywachtwoord", "xyz");
            source.put("security.keystore.bestand", "xyz");
            source.put("security.keystore.wachtwoord", "xyz");
            source.put("security.truststore.bestand", "xyz");
            source.put("security.truststore.wachtwoord", "xyz");
            source.put("verzending.jms.concurrency", "1");
            source.put("jms.broker.lo3.leveringen.queue", "jms.broker.lo3.leveringen.queue");
            return new MapPropertySource("verzending", source);
        }
    }
}
