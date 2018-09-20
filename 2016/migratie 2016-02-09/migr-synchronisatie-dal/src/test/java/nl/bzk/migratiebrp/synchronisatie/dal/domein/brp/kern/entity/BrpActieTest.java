/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.regels.testutils.ReflectionUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BrpActieTest {

    private static final Timestamp TIJDSTIP = new Timestamp(1L);
    private static final Timestamp TIJDSTIP2 = new Timestamp(2L);
    private static final SoortActie SOORT_ACTIE = SoortActie.CONVERSIE_GBA;
    private final Partij partij = new Partij("naam1", 1);
    private final Partij partij2 = new Partij("naam2", 2);
    private final Document document = new Document();
    private final AdministratieveHandeling handeling = new AdministratieveHandeling(
        partij,
        SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
    private final BRPActie brpActie = new BRPActie(SoortActie.CONVERSIE_GBA, handeling, partij, TIJDSTIP);
    private final BRPActie brpActie2 = new BRPActie(SoortActie.CONVERSIE_GBA, handeling, partij, TIJDSTIP);

    @Before
    public void setUp() {
        document.setPartij(partij);
    }

    @Test
    public void soortActie() {
        try {
            brpActie.setSoortActie(null);
            Assert.fail("NullPointerException verwacht");
        } catch (final NullPointerException npe) {
            Assert.assertEquals("soortActie mag niet null zijn", npe.getMessage());
        }

        brpActie.setSoortActie(SOORT_ACTIE);
        Assert.assertEquals(SOORT_ACTIE, brpActie.getSoortActie());
    }

    @Test
    public void addDocument() {
        Assert.assertTrue(brpActie.getDocumentSet().isEmpty());

        brpActie.koppelDocumentViaActieBron(document, null);
        assertEnkeleKoppeling();
        brpActie.koppelDocumentViaActieBron(document, null);
        assertEnkeleKoppeling();
    }

    private void assertEnkeleKoppeling() {
        Assert.assertEquals(1, brpActie.getDocumentSet().size());
        Assert.assertTrue(brpActie.getDocumentSet().contains(document));
        Assert.assertEquals(1, document.getBRPActieSet().size());
        Assert.assertTrue(document.getBRPActieSet().contains(brpActie));

        final ActieBron actieBron1 = (ActieBron) ((Set<?>) ReflectionUtil.getField(brpActie, "actieBronSet")).toArray()[0];
        final ActieBron actieBron2 = (ActieBron) ((Set<?>) ReflectionUtil.getField(document, "actieBronSet")).toArray()[0];

        Assert.assertSame(actieBron1, actieBron2);
    }

    @Test
    public void isInhoudelijkGelijk() {
        Assert.assertTrue(brpActie.isInhoudelijkGelijkAan(brpActie));
        Assert.assertTrue(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setDatumTijdRegistratie(TIJDSTIP);
        brpActie2.setDatumTijdRegistratie(TIJDSTIP);
        brpActie.setPartij(partij);
        brpActie2.setPartij(partij);
        brpActie.setSoortActie(SOORT_ACTIE);
        brpActie2.setSoortActie(SOORT_ACTIE);
        brpActie.koppelDocumentViaActieBron(document, null);
        brpActie2.koppelDocumentViaActieBron(document, null);

        Assert.assertTrue(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkNull() {
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(null));
    }

    @Test
    public void isInhoudelijkGelijkDatumTijdRegistratie() {
        brpActie.setDatumTijdRegistratie(TIJDSTIP2);
        brpActie2.setDatumTijdRegistratie(TIJDSTIP);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setDatumTijdRegistratie(TIJDSTIP);
        brpActie2.setDatumTijdRegistratie(TIJDSTIP2);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkPartij() {
        brpActie.setPartij(partij2);
        brpActie2.setPartij(partij);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setPartij(partij);
        brpActie2.setPartij(partij2);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkSoortActie() {
        brpActie.setSoortActie(SoortActie.CORRECTIE_ADRES);
        brpActie2.setSoortActie(SOORT_ACTIE);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setSoortActie(SOORT_ACTIE);
        brpActie2.setSoortActie(SoortActie.CORRECTIE_ADRES);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkDocumentHistorie() {
        brpActie2.koppelDocumentViaActieBron(document, null);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.koppelDocumentViaActieBron(new Document(), null);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }
}
