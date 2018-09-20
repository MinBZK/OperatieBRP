/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.integratie;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.levering.mutatiecontrole.AbstractRepositoryTestCase;
import nl.bzk.brp.levering.mutatiecontrole.jms.AdministratieveHandelingVerwerker;
import nl.bzk.brp.levering.mutatiecontrole.service.AdministratieveHandelingService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

public class AdministratieveHandelingServiceIntegratieTest extends AbstractRepositoryTestCase {

    private final Logger logger = LoggerFactory.getLogger(AdministratieveHandelingServiceIntegratieTest.class);

    @PersistenceContext
    private EntityManager                     em;

    @Autowired
    private AdministratieveHandelingVerwerker administratieveHandelingVerwerker;

    @Autowired
    private AdministratieveHandelingService administratieveHandelingService;

    @DirtiesContext
    @Test
    public void testPlaatsOnverwerkteAdministratieveHandelingenOpQueue() {

        // given
        List<BigInteger> onverwerkteAdministratieveHandelingen = new ArrayList<BigInteger>();
        onverwerkteAdministratieveHandelingen.add(new BigInteger("10"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("11"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("12"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("13"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("14"));

        final int aantalOnverwerkteAdministratieveHandelingenBeginTest =
            haalAantalOnverwerkteAdministratieveHandelingenOp(onverwerkteAdministratieveHandelingen);

        Assert.assertEquals(onverwerkteAdministratieveHandelingen.size(), aantalOnverwerkteAdministratieveHandelingenBeginTest);

        // when
        administratieveHandelingVerwerker.plaatsAdministratieveHandelingenOpQueue(onverwerkteAdministratieveHandelingen);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // then
        final int aantalOnverwerkteAdministratieveHandelingenEindeTest =
                haalAantalOnverwerkteAdministratieveHandelingenOp(onverwerkteAdministratieveHandelingen);

        Assert.assertNotNull(aantalOnverwerkteAdministratieveHandelingenBeginTest);
        Assert.assertNotNull(aantalOnverwerkteAdministratieveHandelingenEindeTest);
        Assert.assertTrue(onverwerkteAdministratieveHandelingen.size()
                > aantalOnverwerkteAdministratieveHandelingenEindeTest);
        Assert.assertEquals(0, aantalOnverwerkteAdministratieveHandelingenEindeTest);
    }

    /**
     * Haal aantal onverwerkte administratieve handelingen op uit database.
     *
     * @param onverwerkteAdministratieveHandelingen de onverwerkte administratieve handelingen
     * @return de int
     */
    private int haalAantalOnverwerkteAdministratieveHandelingenOp(
            final List<BigInteger> onverwerkteAdministratieveHandelingen)
    {
        StringBuilder collectionOnverwerkt = new StringBuilder("(");
        for (BigInteger onverwerkteAdministratieveHandeling : onverwerkteAdministratieveHandelingen) {
            collectionOnverwerkt.append(onverwerkteAdministratieveHandeling + ",");
        }
        if (collectionOnverwerkt.length() > 2) {
            collectionOnverwerkt.deleteCharAt(collectionOnverwerkt.length() - 1);
        }
        collectionOnverwerkt.append(")");
        final String query =
            "SELECT count(*) FROM kern.admhnd ka WHERE id IN " + collectionOnverwerkt.toString()
                + " AND tsverwerkingmutatie IS NULL";

        BigInteger aantalOnverwerkt = (BigInteger) em.createNativeQuery(query).getSingleResult();

        return aantalOnverwerkt.intValue();
    }

}
