/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.sql.Timestamp;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BrpActieTest {

    private static final Timestamp TIJDSTIP = new Timestamp(1L);
    private static final SoortActie SOORT_ACTIE = SoortActie.CONVERSIE_GBA;
    private final Partij partij = new Partij();
    private final Verdrag verdrag = new Verdrag();
    private final Document document = new Document();
    private final BRPActie brpActie = new BRPActie();
    private final BRPActie brpActie2 = new BRPActie();

    @Before
    public void setUp() {
        document.setPartij(partij);
    }

    @Test
    public void soortActie() {
        brpActie.setSoortActie(null);
        Assert.assertNull(brpActie.getSoortActie());

        brpActie.setSoortActie(SOORT_ACTIE);
        Assert.assertEquals(SOORT_ACTIE, brpActie.getSoortActie());
    }

    @Test
    public void addDocument() {
        final Set<Document> documentSet = brpActie.getDocumentSet();
        Assert.assertTrue(documentSet.isEmpty());

        brpActie.addDocument(document);
        Assert.assertFalse(documentSet.isEmpty());
        Assert.assertTrue(documentSet.size() == 1);
        Assert.assertTrue(documentSet.contains(document));

        brpActie.addDocument(document);
        Assert.assertFalse(documentSet.isEmpty());
        Assert.assertTrue(documentSet.size() == 1);
    }

    @Test
    public void isInhoudelijkGelijk() {
        Assert.assertTrue(brpActie.isInhoudelijkGelijkAan(brpActie));
        Assert.assertTrue(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setDatumTijdOntlening(TIJDSTIP);
        brpActie2.setDatumTijdOntlening(TIJDSTIP);
        brpActie.setDatumTijdRegistratie(TIJDSTIP);
        brpActie2.setDatumTijdRegistratie(TIJDSTIP);
        brpActie.setPartij(partij);
        brpActie2.setPartij(partij);
        brpActie.setSoortActie(SOORT_ACTIE);
        brpActie2.setSoortActie(SOORT_ACTIE);
        brpActie.setVerdrag(verdrag);
        brpActie2.setVerdrag(verdrag);
        brpActie.addDocument(document);
        brpActie2.addDocument(document);

        Assert.assertTrue(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkNull() {
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(null));
    }

    @Test
    public void isInhoudelijkGelijkDatumTijdOntlening() {
        brpActie2.setDatumTijdOntlening(TIJDSTIP);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setDatumTijdOntlening(TIJDSTIP);
        brpActie2.setDatumTijdOntlening(null);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkDatumTijdRegistratie() {
        brpActie2.setDatumTijdRegistratie(TIJDSTIP);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setDatumTijdRegistratie(TIJDSTIP);
        brpActie2.setDatumTijdRegistratie(null);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkPartij() {
        brpActie2.setPartij(partij);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setPartij(partij);
        brpActie2.setPartij(null);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkSoortActie() {
        brpActie2.setSoortActie(SOORT_ACTIE);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setSoortActie(SOORT_ACTIE);
        brpActie2.setSoortActie(null);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkVerdrag() {
        brpActie2.setVerdrag(verdrag);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.setVerdrag(verdrag);
        brpActie2.setVerdrag(null);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }

    @Test
    public void isInhoudelijkGelijkDocumentHistorie() {
        brpActie2.addDocument(document);
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));

        brpActie.addDocument(new Document());
        Assert.assertFalse(brpActie.isInhoudelijkGelijkAan(brpActie2));
    }
}
