/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.bijhouding.domein.SoortActie;
import nl.bzk.brp.bijhouding.domein.SoortDocument;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsAutorisatie;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsSituatie;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieSituatieCheck;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BijhoudingsAutorisatieSituatieCheckTest {

    private Long          categorieSoortActieA = 1L;
    private Long          categorieSoortActieB = 2L;
    private Long          categorieSoortActieC = 3L;

    private SoortActie    soortActieA;
    private SoortActie    soortActieB;
    private SoortActie    soortActieC;
    private SoortActie    soortActieD;

    private Long          categorieDocumentA   = 1L;
    private Long          categorieDocumentB   = 2L;
    private Long          categorieDocumentC   = 3L;

    private SoortDocument soortDocumentA;
    private SoortDocument soortDocumentB;
    private SoortDocument soortDocumentC;
    private SoortDocument soortDocumentD;

    @Before
    public void init() {
        soortActieA = new SoortActie(1L, "Inschrijving huwelijk", categorieSoortActieA);
        soortActieB = new SoortActie(2L, "Inschrijving op adres", categorieSoortActieA);
        soortActieC = new SoortActie(3L, "Geboorte kind", categorieSoortActieB);
        soortActieD = new SoortActie(4L, "Vertrek naar buitenland", categorieSoortActieC);

        soortDocumentA = new SoortDocument(1L, "Nederlandse huwelijks akte", categorieDocumentA);
        soortDocumentB = new SoortDocument(2L, "Permissie hoofdbewoner", categorieDocumentA);
        soortDocumentC = new SoortDocument(3L, "Nederlandse geboorte akte", categorieDocumentB);
        soortDocumentD = new SoortDocument(4L, "Buitelands paspoort", categorieDocumentC);
    }

    @Test
    public void testGeenBijhoudingsSituatie() {
        final BijhoudingsAutorisatieSituatieCheck check =
            new BijhoudingsAutorisatieSituatieCheck(soortActieA, soortDocumentA);

        // Geen bijhoudings situatie betekent, elke actie mag tegen elk document:
        BijhoudingsAutorisatie bijhAutorisatie = new BijhoudingsAutorisatie();
        assertTrue(check.apply(bijhAutorisatie));

        //Variant waarbij bijhoudingssituatie leeg is:
        ReflectionTestUtils.setField(bijhAutorisatie, "bijhoudingsSituaties", new HashSet<BijhoudingsSituatie>());
        assertTrue(check.apply(bijhAutorisatie));
    }

    @Test
    public void testSoortActieTegenSoortDocument() {
        // Voor soortActieA heb je soortDocumentA nodig.
        BijhoudingsAutorisatie bijhAutorisatie =
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
        BijhoudingsAutorisatie bijhAutorisatie =
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
        BijhoudingsAutorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(soortActieA, null, null, categorieDocumentA);

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
        BijhoudingsAutorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(null, null, categorieSoortActieA, categorieDocumentA);

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
        BijhoudingsAutorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(soortActieA, null, null, categorieDocumentA);

        // Een bijhouding die gedaan wordt zonder document. Dus soortActie = null en soortDocument = null.
        BijhoudingsAutorisatieSituatieCheck check = new BijhoudingsAutorisatieSituatieCheck(null, null);

        // Exceptie verwacht.
        check.apply(bijhAutorisatie);
    }

    @Test
    public void testBijhoudingVanAndereSoortActieNietToegestaan() {
        // Voor soortActieA heb je categorieDocumentA nodig.
        BijhoudingsAutorisatie bijhAutorisatie =
            initialiseerSituatieVoorAutorisatie(soortActieA, null, null, categorieDocumentA);

        // Een bijhouding die gedaan wordt zonder document.
        BijhoudingsAutorisatieSituatieCheck check =
            new BijhoudingsAutorisatieSituatieCheck(soortActieC, null);

        assertFalse(check.apply(bijhAutorisatie));
    }

    private BijhoudingsAutorisatie initialiseerSituatieVoorAutorisatie(final SoortActie soortActie,
            final SoortDocument soortDocument, final Long categorieActie, final Long categorieDocument)
    {
        final BijhoudingsAutorisatie bijhAutorisatie = new BijhoudingsAutorisatie();
        Set<BijhoudingsSituatie> situaties = new HashSet<BijhoudingsSituatie>();
        BijhoudingsSituatie situatie = new BijhoudingsSituatie();
        ReflectionTestUtils.setField(situatie, "soortActie", soortActie);
        ReflectionTestUtils.setField(situatie, "soortDocument", soortDocument);
        ReflectionTestUtils.setField(situatie, "categorieSrtActie", categorieActie);
        ReflectionTestUtils.setField(situatie, "categorieSoortDocument", categorieDocument);
        situaties.add(situatie);
        ReflectionTestUtils.setField(bijhAutorisatie, "bijhoudingsSituaties", situaties);
        return bijhAutorisatie;
    }
}
