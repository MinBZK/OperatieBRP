/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.bezemwagen.jms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;

@Category(OverslaanBijInMemoryDatabase.class)
public class AdministratieveHandelingVerwerkerIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private AdministratieveHandelingVerwerker administratieveHandelingVerwerker;

    @Inject
    private AdministratieveHandelingListener administratieveHandelingListener;

    @DirtiesContext
    @Test
    public final void plaatsAdministratieveHandelingenOpQueueTest() {

        // given
        final List<BigInteger> onverwerkteAdministratieveHandelingen = new ArrayList<>();
        onverwerkteAdministratieveHandelingen.add(BigInteger.ONE);
        onverwerkteAdministratieveHandelingen.add(new BigInteger("3"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("4"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("5"));

        final int aantalOnverwerktBeginTest = onverwerkteAdministratieveHandelingen.size();
        final int aantalVerwerktBeginTest = administratieveHandelingListener.getVerwerkteAdministratieveHandelingen();

        // when
        administratieveHandelingVerwerker.plaatsAdministratieveHandelingenOpQueue(onverwerkteAdministratieveHandelingen);

        try {
            Thread.sleep(5000);
        } catch (final InterruptedException e) {
            LOGGER.error("Thread sleep faalde! ", e);
        }

        // then
        final int aantalVerwerktEindTest = administratieveHandelingListener.getVerwerkteAdministratieveHandelingen();
        final int aantalVerwerkt = aantalVerwerktEindTest - aantalVerwerktBeginTest;

        Assert.assertNotNull(aantalVerwerktBeginTest);
        Assert.assertNotNull(aantalVerwerktEindTest);
        Assert.assertNotNull(aantalOnverwerktBeginTest);
        Assert.assertNotNull(aantalVerwerkt);
        Assert.assertTrue(aantalVerwerktBeginTest < aantalVerwerktEindTest);
        Assert.assertEquals(aantalOnverwerktBeginTest, aantalVerwerkt);
    }

    @Override
    @Inject
    @Named("alleenLezenDataSource")
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
    }

}
