/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.ahpublicatie.it;

import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.delivery.dataaccess.EmbeddedDatabaseConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

/**
 */
@ContextConfiguration(classes = ApplicatieContextTest.AhPublicatieTestContext.class)
public class ApplicatieContextTest extends AbstractDataAccessTest {

    @Test
    public void test() {
    }

    @Configuration
    @Import(EmbeddedDatabaseConfiguration.class)
    @ImportResource(value =  {"classpath:admhndpublicatie.xml"} )
    public static class AhPublicatieTestContext {
    }
}
