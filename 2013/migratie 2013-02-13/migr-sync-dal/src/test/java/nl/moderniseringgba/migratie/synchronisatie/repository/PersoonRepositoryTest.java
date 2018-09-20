/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.ExpectedAfter;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class PersoonRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private PersoonRepository persoonRepository;

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void vraagBepaaldPersoonOp() {
        final Persoon persoon =
                persoonRepository.findByAdministratienummer(new BigDecimal("123455789"), SoortPersoon.INGESCHREVENE)
                        .get(0);
        assertNotNull(persoon);
        assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
    }

    @Transactional
    @Test
    @ExpectedAfter("PersoonExpected.xml")
    public void insertEenPersoon() {
        final Persoon persoon = new Persoon();
        persoon.setSoortPersoon(SoortPersoon.INGESCHREVENE);
        persoonRepository.save(persoon);
        assertEquals(1, persoon.getId().longValue());
    }

    @InsertBefore({ "PersoonHistTestPersoonData.xml", "PersoonHistTestHistData.xml" })
    @Test
    public void vraagHistorischdPersoonOp() {
        final List<Persoon> actueel =
                persoonRepository.findByAdministratienummerHistorisch(new BigDecimal("4445556667"),
                        SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(actueel);
        Assert.assertTrue(actueel.isEmpty()); // Actueel

        final List<Persoon> persoon =
                persoonRepository.findByAdministratienummerHistorisch(new BigDecimal("3334445556"),
                        SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(persoon);
        Assert.assertFalse(persoon.isEmpty());

        final List<Persoon> vervallen =
                persoonRepository.findByAdministratienummerHistorisch(new BigDecimal("2223334445"),
                        SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(vervallen);
        Assert.assertTrue(vervallen.isEmpty());
    }
}
