/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;

import org.junit.Test;

public final class BrpGroepValidatorTest {

    @Test
    @Preconditie(Precondities.PRE022)
    public void testValideerVoorvoegselScheidingsteken() {
        final String groep = "groep";
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(null, null, groep);
        BrpGroepValidator.valideerVoorvoegselScheidingsteken("van", ' ', groep);
        try {
            BrpGroepValidator.valideerVoorvoegselScheidingsteken("van", null, groep);
            fail("Exceptie verwacht omdat scheidingsteken leeg is, maar voorvoegsel niet");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE022.name()));
            assertTrue(e.getMessage().contains(groep));
        }
        try {
            BrpGroepValidator.valideerVoorvoegselScheidingsteken(null, ' ', groep);
            fail("Exceptie verwacht omdat voorvoegsel leeg is, maar scheidingsteken niet");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE022.name()));
            assertTrue(e.getMessage().contains(groep));
        }
        try {
            BrpGroepValidator.valideerVoorvoegselScheidingsteken("", ' ', groep);
            fail("Exceptie verwacht omdat voorvoegsel leeg is, maar scheidingsteken niet");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE022.name()));
            assertTrue(e.getMessage().contains(groep));
        }
        try {
            BrpGroepValidator.valideerVoorvoegselScheidingsteken("   ", ' ', groep);
            fail("Exceptie verwacht omdat voorvoegsel leeg is, maar scheidingsteken niet");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE022.name()));
            assertTrue(e.getMessage().contains(groep));
        }
    }

    @Test
    public void testValideerBuitenlandsePlaatsNietNederland() {
        final String groep = "groep";
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(null, BrpLandCode.NEDERLAND, groep);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(null, new BrpLandCode(Integer.valueOf("1234")),
                groep);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland("plaats", new BrpLandCode(Integer.valueOf("1234")),
                groep);
        try {
            BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland("plaats", BrpLandCode.NEDERLAND, groep);
            fail("Exceptie verwacht omdat buitenlandse plaats is gevuld, maar land is Nederland");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE004.name()));
            assertTrue(e.getMessage().contains(groep));
        }
    }

    @Test
    public void testValideerGemeenteInNederland() {
        final String groep = "groep";
        BrpGroepValidator.valideerGemeenteInNederland(null, null, groep);
        BrpGroepValidator.valideerGemeenteInNederland(null, BrpLandCode.NEDERLAND, groep);
        final BrpGemeenteCode gemeente = new BrpGemeenteCode(new BigDecimal("1234"));
        BrpGroepValidator.valideerGemeenteInNederland(gemeente, BrpLandCode.NEDERLAND, groep);
        try {
            BrpGroepValidator.valideerGemeenteInNederland(gemeente, null, groep);
            fail("Exceptie verwacht omdat land niet Nederland is");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE003.name()));
            assertTrue(e.getMessage().contains(groep));
        }
        try {
            BrpGroepValidator.valideerGemeenteInNederland(gemeente, new BrpLandCode(Integer.valueOf("4321")), groep);
            fail("Exceptie verwacht omdat land niet Nederland is");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE003.name()));
            assertTrue(e.getMessage().contains(groep));
        }
    }
}
