/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import org.junit.Assert;
import org.junit.Test;

public class PersoonsantwoordTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final JsonStringSerializer serializer = new JsonStringSerializer();

    @Test
    public void test() {
        final Persoonsantwoord subject = new Persoonsantwoord();
        assertGelijk(subject, reserialize(subject));

        subject.setFoutreden("A");
        assertGelijk(subject, reserialize(subject));

        subject.setInhoud("inhoud");
        assertGelijk(subject, reserialize(subject));
    }

    private void assertGelijk(final Persoonsantwoord expected, final Persoonsantwoord actual) {
        Assert.assertEquals("foutreden", expected.getFoutreden(), actual.getFoutreden());
        Assert.assertEquals("inhoud", expected.getInhoud(), actual.getInhoud());
    }

    private Persoonsantwoord reserialize(final Persoonsantwoord object) {
        final String serialized = serializer.serialiseerNaarString(object);
        LOGGER.info("Serialized: {}", serialized);
        return serializer.deserialiseerVanuitString(serialized, Persoonsantwoord.class);
    }
}
