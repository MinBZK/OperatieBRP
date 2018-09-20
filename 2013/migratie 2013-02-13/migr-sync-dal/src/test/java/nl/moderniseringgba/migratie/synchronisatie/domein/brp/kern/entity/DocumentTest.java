/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DocumentTest {
    private static final String AKTENUMMER = "aktenummer";
    private static final String IDENTIFICATIE = "identificatie";
    private static final String OMSCHRIJVING = "omschrijving";

    private final Document document = new Document();
    private final Document document2 = new Document();
    private final SoortDocument soortDocument = new SoortDocument();
    private final Partij partij = new Partij();
    private final DocumentHistorie docHistorie = new DocumentHistorie();

    @Before
    public void setUp() {
        soortDocument.setCategorieSoortDocument(CategorieSoortDocument.NEDERLANDSE_AKTE);
        soortDocument.setOmschrijving(OMSCHRIJVING);
        docHistorie.setIdentificatie(IDENTIFICATIE);
    }

    @Test
    public void compare() {
        Assert.assertEquals(0, Document.COMPARATOR.compare(document, document));
        document.setAktenummer(AKTENUMMER);
        document.setIdentificatie(IDENTIFICATIE);
        document.setOmschrijving(OMSCHRIJVING);
        document.setSoortDocument(soortDocument);
        Assert.assertEquals(0, Document.COMPARATOR.compare(document, document));

        soortDocument.setOmschrijving(null);
        document2.setAktenummer(AKTENUMMER);
        document2.setIdentificatie(IDENTIFICATIE);
        document2.setOmschrijving(AKTENUMMER);
        document2.setSoortDocument(soortDocument);
        Assert.assertFalse(Document.COMPARATOR.compare(document, document2) == 0);
    }

    @Test
    public void addBRPActie() {
        final BRPActie brpActie = new BRPActie();
        final Set<BRPActie> brpActies = document.getBRPActieSet();
        Assert.assertTrue(brpActies.isEmpty());

        document.addBRPActie(brpActie);
        Assert.assertFalse(brpActies.isEmpty());
        Assert.assertTrue(brpActies.size() == 1);
        Assert.assertTrue(brpActies.contains(brpActie));

        document.addBRPActie(brpActie);
        Assert.assertFalse(brpActies.isEmpty());
        Assert.assertTrue(brpActies.size() == 1);
    }

    @Test
    public void addDocumentHistorie() {
        final Set<DocumentHistorie> docHistorieSet = document.getDocumentHistorieSet();
        Assert.assertTrue(docHistorieSet.isEmpty());

        document.addDocumentHistorie(docHistorie);
        Assert.assertFalse(docHistorieSet.isEmpty());
        Assert.assertTrue(docHistorieSet.size() == 1);
        Assert.assertTrue(docHistorieSet.contains(docHistorie));
    }

    @Test
    public void isInhoudelijkGelijk() {
        Assert.assertTrue(document.isInhoudelijkGelijkAan(document));
        Assert.assertTrue(document.isInhoudelijkGelijkAan(document2));

        document.setAktenummer(AKTENUMMER);
        document2.setAktenummer(AKTENUMMER);
        document.setIdentificatie(IDENTIFICATIE);
        document2.setIdentificatie(IDENTIFICATIE);
        document.setOmschrijving(OMSCHRIJVING);
        document2.setOmschrijving(OMSCHRIJVING);
        document.setPartij(partij);
        document2.setPartij(partij);
        document.setSoortDocument(soortDocument);
        document2.setSoortDocument(soortDocument);
        document.addDocumentHistorie(docHistorie);
        document2.addDocumentHistorie(docHistorie);

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
        document2.setAktenummer(null);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));
    }

    @Test
    public void isInhoudelijkGelijkIdentificatie() {
        document2.setIdentificatie(IDENTIFICATIE);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));

        document.setIdentificatie(IDENTIFICATIE);
        document2.setIdentificatie(null);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));
    }

    @Test
    public void isInhoudelijkGelijkOmschrijving() {
        document2.setOmschrijving(OMSCHRIJVING);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));

        document.setOmschrijving(OMSCHRIJVING);
        document2.setOmschrijving(null);
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
        document2.setSoortDocument(null);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));
    }

    @Test
    public void isInhoudelijkGelijkDocumentHistorie() {
        document2.addDocumentHistorie(docHistorie);
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));

        document.addDocumentHistorie(new DocumentHistorie());
        Assert.assertFalse(document.isInhoudelijkGelijkAan(document2));
    }
}
