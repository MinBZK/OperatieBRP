/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.jms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.levering.mutatiecontrole.AbstractRepositoryTestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;


public class AdministratieveHandelingVerwerkerTest extends AbstractRepositoryTestCase {

    private final Logger logger = LoggerFactory.getLogger(AdministratieveHandelingVerwerkerTest.class);

    @Autowired
    private AdministratieveHandelingVerwerker administratieveHandelingVerwerker;

    @Autowired
    private AdministratieveHandelingListener administratieveHandelingListener;

    @DirtiesContext
    @Test
    public void plaatsAdministratieveHandelingenOpQueueTest() {

        // given
        List<BigInteger> onverwerkteAdministratieveHandelingen = new ArrayList<BigInteger>();
        onverwerkteAdministratieveHandelingen.add(new BigInteger("1"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("3"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("4"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("7"));

        final int aantalOnverwerktBeginTest = onverwerkteAdministratieveHandelingen.size();
        final int aantalVerwerktBeginTest = administratieveHandelingListener.getVerwerkteAdministratieveHandelingen();

        // when
        administratieveHandelingVerwerker.plaatsAdministratieveHandelingenOpQueue(onverwerkteAdministratieveHandelingen);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

}
