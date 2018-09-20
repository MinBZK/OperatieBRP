/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieSituatieCheck;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.domein.autaut.Bijhoudingssituatie;
import nl.bzk.brp.domein.kern.CategorieSoortActie;
import nl.bzk.brp.domein.kern.CategorieSoortDocument;
import nl.bzk.brp.domein.kern.SoortActie;
import nl.bzk.brp.domein.kern.SoortDocument;
import org.junit.Before;
import org.junit.Test;


public class BijhoudingsAutorisatieSituatieCheckTest {

    private final DomeinObjectFactory       domeinObjectFactory  = new PersistentDomeinObjectFactory();

    private final CategorieSoortActie categorieSoortActieA = CategorieSoortActie.SIMPELE_BIJHOUDING;

    private SoortActie                soortActieA;
    private SoortActie                soortActieB;
    private SoortActie                soortActieC;
    private SoortActie                soortActieD;

    private SoortDocument             soortDocumentA;
    private SoortDocument             soortDocumentB;
    private SoortDocument             soortDocumentC;
    private SoortDocument             soortDocumentD;

    @Before
    public void init() {
        soortActieA = nieuwSoortActie(1, "Inschrijving huwelijk", categorieSoortActieA);
        soortActieB = nieuwSoortActie(2, "Inschrijving op adres", categorieSoortActieA);
        soortActieC = nieuwSoortActie(3, "Geboorte kind", categorieSoortActieA);
        soortActieD = nieuwSoortActie(4, "Vertrek naar buitenland", categorieSoortActieA);

        soortDocumentA = nieuwSoortDocument(1, "Nederlandse huwelijks akte", CategorieSoortDocument.NEDERLANDSE_AKTE);
        soortDocumentB = nieuwSoortDocument(2, "Permissie hoofdbewoner", CategorieSoortDocument.NEDERLANDSE_AKTE);
        soortDocumentC = nieuwSoortDocument(3, "Nederlandse geboorte akte", CategorieSoortDocument.DUMMY);
        soortDocumentD = nieuwSoortDocument(4, "Buitelands paspoort", CategorieSoortDocument.DUMMY);
    }

    /**
     * @return
     */
    private SoortDocument nieuwSoortDocument(final Integer id, final String omschrijving,
            final CategorieSoortDocument soortDocument)
    {
        SoortDocument resultaat = domeinObjectFactory.createSoortDocument();
        resultaat.setID(id);
        resultaat.setOmschrijving(omschrijving);
        resultaat.setCategorieSoortDocument(soortDocument);
        return resultaat;
    }

    /**
     * @param id TODO
     * @param omschrijving TODO
     * @return
     */
    private SoortActie nieuwSoortActie(final int id, final String omschrijving, final CategorieSoortActie categorie) {
        SoortActie resultaat = domeinObjectFactory.createSoortActie();
        resultaat.setID(id);
        resultaat.setNaam(omschrijving);
        resultaat.setCategorieSoortActie(categorie);
        return resultaat;
    }

    @Test
    public void testGeenBijhoudingsSituatie() {
        final BijhoudingsAutorisatieSituatieCheck check =
            new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentA);

        // Geen bijhoudings situatie betekent, elke actie mag tegen elk document:
        Bijhoudingsautorisatie bijhAutorisatie = domeinObjectFactory.createBijhoudingsautorisatie();
        assertTrue(check.apply(bijhAutorisatie));

        // Variant waarbij bijhoudingssituatie leeg is:
        assertTrue(check.apply(bijhAutorisatie));
    }

    @Test
    public void testSoortActieTegenSoortDocument() {
        // Voor soortActieA heb je soortDocumentA nodig.
        Bijhoudingsautorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(soortActieA, soortDocumentA, null, null);

        BijhoudingsAutorisatieSituatieCheck check =
            new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentA);

        assertTrue(check.apply(bijhAutorisatie));

        // SortDocumentB is van dezelfde categorie.
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentB);

        assertFalse(check.apply(bijhAutorisatie));

        // SortDocumentB is van een andere categorie.
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentD);

        assertFalse(check.apply(bijhAutorisatie));

        // SortActieB is van dezelfde categorie als soortActieA.
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieB, soortDocumentA);

        assertFalse(check.apply(bijhAutorisatie));
    }

    @Test
    public void testCategorieActieTegenSoortDocument() {
        // Voor categorieSoortActieA heb je soortDocumentA nodig.
        Bijhoudingsautorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(null, soortDocumentA, categorieSoortActieA, null);

        BijhoudingsAutorisatieSituatieCheck check =
            new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentA);

        assertTrue(check.apply(bijhAutorisatie));

        check = new BijhoudingsAutorisatieSituatieCheck(
        // SoortActieB is van dezelfde categorie als soortActieA
                soortActieB, soortDocumentA);

        assertTrue(check.apply(bijhAutorisatie));

        check = new BijhoudingsAutorisatieSituatieCheck(
        // SoortActieB is van dezelfde categorie als soortActieA
                soortActieB, soortDocumentC);

        assertFalse(check.apply(bijhAutorisatie));

        check = new BijhoudingsAutorisatieSituatieCheck(
        // SortDocumentB is van dezelfde categorie. Maar expliciet is soortDocumentA nodig.
                soortActieA, soortDocumentB);

        assertFalse(check.apply(bijhAutorisatie));

        // SortDocumentB is van een andere categorie.
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentD);

        assertFalse(check.apply(bijhAutorisatie));

        // SortDocumentB is van een andere categorie.
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieC, soortDocumentD);

        assertFalse(check.apply(bijhAutorisatie));
    }

    @Test
    public void testSoortActieTegenCategorieDocument() {
        // Voor soortActieA heb je categorieDocumentA nodig.
        Bijhoudingsautorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(soortActieA, null, null, CategorieSoortDocument.NEDERLANDSE_AKTE);

        BijhoudingsAutorisatieSituatieCheck check =
            new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentA);

        assertTrue(check.apply(bijhAutorisatie));

        check = new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentC);

        assertFalse(check.apply(bijhAutorisatie));

        // SoortDocumentB is van categorie A
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentB);

        assertTrue(check.apply(bijhAutorisatie));

        // SoortDocumentB is van categorie A
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieD, soortDocumentB);

        assertFalse(check.apply(bijhAutorisatie));
    }

    @Test
    public void testCategorieActieTegenCategorieDocument() {
        // Voor categorieSoortActieA heb je categorieDocumentA nodig.
        Bijhoudingsautorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(null, null, categorieSoortActieA,
                    CategorieSoortDocument.NEDERLANDSE_AKTE);

        BijhoudingsAutorisatieSituatieCheck check =
            new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentA);

        assertTrue(check.apply(bijhAutorisatie));

        check = new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentC);

        assertFalse(check.apply(bijhAutorisatie));

        // SoortDocumentB is van categorie A
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentB);

        assertTrue(check.apply(bijhAutorisatie));

        // SoortDocumentB is van categorie A
        check = new BijhoudingsAutorisatieSituatieCheck(soortActieC, soortDocumentC);

        assertFalse(check.apply(bijhAutorisatie));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSoortActieEnDocumentLeegBijhSituatieGedefinieerd() {
        // Voor soortActieA heb je categorieDocumentA nodig.
        Bijhoudingsautorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(soortActieA, null, null, CategorieSoortDocument.NEDERLANDSE_AKTE);

        // Een bijhouding die gedaan wordt zonder document. Dus soortActie = null en soortDocument = null.
        BijhoudingsAutorisatieSituatieCheck check = new BijhoudingsAutorisatieSituatieCheck(null, null);

        // Exceptie verwacht.
        check.apply(bijhAutorisatie);
    }

    @Test
    public void testBijhoudingVanAndereSoortActieNietToegestaan() {
        // Voor soortActieA heb je categorieDocumentA nodig.
        Bijhoudingsautorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(soortActieA, null, null, CategorieSoortDocument.NEDERLANDSE_AKTE);

        // Een bijhouding die gedaan wordt zonder document.
        BijhoudingsAutorisatieSituatieCheck check = new BijhoudingsAutorisatieSituatieCheck(soortActieC, null);

        assertFalse(check.apply(bijhAutorisatie));
    }

    private Bijhoudingsautorisatie initialiseerSituatieVoorAutorisatie(final SoortActie soortActie,
            final SoortDocument soortDocument, final CategorieSoortActie categorieActie,
            final CategorieSoortDocument categorieDocument)
    {
        final Bijhoudingsautorisatie bijhAutorisatie = domeinObjectFactory.createBijhoudingsautorisatie();
        Bijhoudingssituatie situatie = domeinObjectFactory.createBijhoudingssituatie();
        situatie.setSoortActie(soortActie);
        situatie.setSoortDocument(soortDocument);
        situatie.setCategorieSoortActie(categorieActie);
        situatie.setCategorieSoortDocument(categorieDocument);
        bijhAutorisatie.addBijhoudingssituatie(situatie);
        return bijhAutorisatie;
    }
}
