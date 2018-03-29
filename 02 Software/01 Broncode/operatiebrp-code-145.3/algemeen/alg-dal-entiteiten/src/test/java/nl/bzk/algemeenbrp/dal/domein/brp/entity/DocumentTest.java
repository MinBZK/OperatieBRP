/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static nl.bzk.algemeenbrp.dal.domein.brp.ReflectionUtil.getField;

import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class DocumentTest {
    private static final String AKTENUMMER = "aktenummer";
    private static final String AKTENUMMER2 = "aktenummer2";
    private static final String OMSCHRIJVING = "omschrijving";
    private static final String OMSCHRIJVING2 = "omschrijving2";
    private static final String OMSCHRIJVING_NULL = " ";

    private final Document         document       = new Document();
    private final Document         document2      = new Document();
    private final SoortDocument    soortDocument  = new SoortDocument("naam1", OMSCHRIJVING);
    private final SoortDocument    soortDocument2 = new SoortDocument("naam2", OMSCHRIJVING);
    private final Partij           partij         = new Partij();

    @Test
    public void compare() {
        Assert.assertEquals(0, Document.COMPARATOR.compare(document, document));
        document.setAktenummer(AKTENUMMER);
        document.setOmschrijving(OMSCHRIJVING);
        document.setSoortDocument(soortDocument);
        Assert.assertEquals(0, Document.COMPARATOR.compare(document, document));

        soortDocument.setOmschrijving(OMSCHRIJVING_NULL);
        document2.setAktenummer(AKTENUMMER);
        document2.setOmschrijving(AKTENUMMER);
        document2.setSoortDocument(soortDocument);
        Assert.assertFalse(Document.COMPARATOR.compare(document, document2) == 0);
    }

    @Test
    public void addActieBron() {
        final BRPActie brpActie = new BRPActie();
        Assert.assertTrue(document.getBRPActieSet().isEmpty());

        final ActieBron actieBron1 = new ActieBron(brpActie);
        actieBron1.setDocument(document);
        document.addActieBron(actieBron1);
        brpActie.addActieBron(actieBron1);

        assertEnkeleKoppeling(document, brpActie);

        final ActieBron actieBron2 = new ActieBron(brpActie);
        actieBron2.setDocument(document);
        document.addActieBron(actieBron2);
        brpActie.addActieBron(actieBron2);

        assertEnkeleKoppeling(document, brpActie);
    }

    private void assertEnkeleKoppeling(final Document document, final BRPActie brpActie) {
        Assert.assertTrue(document.getBRPActieSet().size() == 1);
        Assert.assertTrue(document.getBRPActieSet().contains(brpActie));
        Assert.assertTrue(brpActie.getDocumentSet().size() == 1);
        Assert.assertTrue(brpActie.getDocumentSet().contains(document));

        final ActieBron actieBron1 = (ActieBron) ((Set<?>) getField(document, "actieBronSet")).toArray()[0];
        final ActieBron actieBron2 = (ActieBron) ((Set<?>) getField(brpActie, "actieBronSet")).toArray()[0];

        Assert.assertSame(actieBron1, actieBron2);
    }

    @Test
    public void isInhoudelijkGelijk() {
        Assert.assertTrue(document.isInhoudelijkGelijkAan(document));
        Assert.assertTrue(document.isInhoudelijkGelijkAan(document2));

        document.setAktenummer(AKTENUMMER);
        document2.setAktenummer(AKTENUMMER);
        document.setOmschrijving(OMSCHRIJVING);
        document2.setOmschrijving(OMSCHRIJVING);
        document.setPartij(partij);
        document2.setPartij(partij);
        document.setSoortDocument(soortDocument);
        document2.setSoortDocument(soortDocument);

        Assert.assertTrue(document.isInhoudelijkGelijkAan(document2));
    }

    @Test
    public void isInhoudelijkGelijkNull() {
        Assert.assertFalse(document.isInhoudelijkGelijkAan(null));
    }

    @Test
    public void isInhoudelijkGelijkAktenummer() {
        document2.setAktenummer(AKTENUMMER);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));

        document.setAktenummer(AKTENUMMER);
        document2.setAktenummer(AKTENUMMER2);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));
    }

    @Test
    public void isInhoudelijkGelijkOmschrijving() {
        document2.setOmschrijving(OMSCHRIJVING);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));

        document.setOmschrijving(OMSCHRIJVING);
        document2.setOmschrijving(OMSCHRIJVING2);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));
    }

    @Test
    public void isInhoudelijkGelijkPartij() {
        document2.setPartij(partij);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));

        document.setPartij(partij);
        document2.setPartij(null);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));
    }

    @Test
    public void isInhoudelijkGelijkSoortDocument() {
        document2.setSoortDocument(soortDocument);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));

        document.setSoortDocument(soortDocument);
        document2.setSoortDocument(soortDocument2);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));
    }
}
