/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepValidator;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

import org.junit.Test;

public final class BrpGroepValidatorTest {

    private static final BrpGroepInhoud GROEP_INHOUD = new TestBrpGroepInhoud();
    private static final String GROEP_NAAM = GROEP_INHOUD.getClass().getName();

    @Test
    @Preconditie(SoortMeldingCode.PRE022)
    public void testValideerVoorvoegselScheidingsteken() {
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(null, null, GROEP_INHOUD);
        BrpGroepValidator.valideerVoorvoegselScheidingsteken("van", ' ', GROEP_INHOUD);
        try {
            BrpGroepValidator.valideerVoorvoegselScheidingsteken("van", null, GROEP_INHOUD);
            fail("Exceptie verwacht omdat scheidingsteken leeg is, maar voorvoegsel niet");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE022.name()));
            assertTrue(e.getGroepen().contains(GROEP_NAAM));
        }
        try {
            BrpGroepValidator.valideerVoorvoegselScheidingsteken(null, ' ', GROEP_INHOUD);
            fail("Exceptie verwacht omdat voorvoegsel leeg is, maar scheidingsteken niet");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE022.name()));
            assertTrue(e.getGroepen().contains(GROEP_NAAM));
        }
        try {
            BrpGroepValidator.valideerVoorvoegselScheidingsteken("", ' ', GROEP_INHOUD);
            fail("Exceptie verwacht omdat voorvoegsel leeg is, maar scheidingsteken niet");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE022.name()));
            assertTrue(e.getGroepen().contains(GROEP_NAAM));
        }
        try {
            BrpGroepValidator.valideerVoorvoegselScheidingsteken("   ", ' ', GROEP_INHOUD);
            fail("Exceptie verwacht omdat voorvoegsel leeg is, maar scheidingsteken niet");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE022.name()));
            assertTrue(e.getGroepen().contains(GROEP_NAAM));
        }
    }

    @Test
    public void testValideerBuitenlandsePlaatsNietNederland() {
        final short landOfGebiedId = 1234;
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(null, BrpLandOfGebiedCode.NEDERLAND, GROEP_INHOUD);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(null, new BrpLandOfGebiedCode(landOfGebiedId), GROEP_INHOUD);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(new BrpString("plaats"), new BrpLandOfGebiedCode(landOfGebiedId), GROEP_INHOUD);
        try {
            BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(new BrpString("plaats"), BrpLandOfGebiedCode.NEDERLAND, GROEP_INHOUD);
            fail("Exceptie verwacht omdat buitenlandse plaats is gevuld, maar land is Nederland");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE004.name()));
            assertTrue(e.getGroepen().contains(GROEP_NAAM));
        }
    }

    @Test
    public void testValideerGemeenteInNederland() {
        BrpGroepValidator.valideerGemeenteInNederland(null, null, GROEP_INHOUD);
        BrpGroepValidator.valideerGemeenteInNederland(null, BrpLandOfGebiedCode.NEDERLAND, GROEP_INHOUD);
        final BrpGemeenteCode gemeente = new BrpGemeenteCode(Short.parseShort("1234"));
        BrpGroepValidator.valideerGemeenteInNederland(gemeente, BrpLandOfGebiedCode.NEDERLAND, GROEP_INHOUD);
        try {
            BrpGroepValidator.valideerGemeenteInNederland(gemeente, null, GROEP_INHOUD);
            fail("Exceptie verwacht omdat land niet Nederland is");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE003.name()));
            assertTrue(e.getGroepen().contains(GROEP_NAAM));
        }
        try {
            BrpGroepValidator.valideerGemeenteInNederland(gemeente, new BrpLandOfGebiedCode(Short.parseShort("4321")), GROEP_INHOUD);
            fail("Exceptie verwacht omdat land niet Nederland is");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE003.name()));
            assertTrue(e.getGroepen().contains(GROEP_NAAM));
        }
    }

    private static class TestBrpGroepInhoud extends AbstractBrpGroepInhoud {

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
         */
        @Override
        public boolean isLeeg() {
            return false;
        }

    }
}
