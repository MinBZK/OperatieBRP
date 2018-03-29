/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.*;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen voor {@link PersoonSamengesteldeNaamHistorie}.
 */
public class PersoonSamengesteldeNaamHistorieTest {

    private Persoon persoon;

    @Before
    public void setup() {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
    }

    @Test
    public void testIsInhoudelijkGelijkAan() {
        final PersoonSamengesteldeNaamHistorie voorkomen1 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen2 = maakTemplateVoorkomen();
        assertTrue(voorkomen1.isInhoudelijkGelijkAan(voorkomen2));
    }

    @Test
    public void testIsInhoudelijkGelijkAanNull() {
        final PersoonSamengesteldeNaamHistorie voorkomen1 = maakTemplateVoorkomen();
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(null));
    }

    @Test
    public void testIsInhoudelijkGelijkAanZichzelf() {
        final PersoonSamengesteldeNaamHistorie voorkomen1 = maakTemplateVoorkomen();
        assertTrue(voorkomen1.isInhoudelijkGelijkAan(voorkomen1));
    }

    @Test
    public void testIsInhoudelijkGelijkAanVariaties() {
        final PersoonSamengesteldeNaamHistorie voorkomen1 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen2 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen3 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen4 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen5 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen6 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen7 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen8 = maakTemplateVoorkomen();
        final PersoonSamengesteldeNaamHistorie voorkomen9 = maakTemplateVoorkomen();
        voorkomen2.setGeslachtsnaamstam(voorkomen2.getGeslachtsnaamstam() + "X");
        voorkomen3.setIndicatieAfgeleid(!voorkomen3.getIndicatieAfgeleid());
        voorkomen4.setIndicatieNamenreeks(!voorkomen4.getIndicatieNamenreeks());
        voorkomen5.setScheidingsteken('X');
        voorkomen6.setVoornamen(voorkomen6.getVoornamen() + " voornaam3");
        voorkomen7.setVoorvoegsel(voorkomen7.getVoorvoegsel() + "X");
        voorkomen8.setAdellijkeTitel(AdellijkeTitel.B);
        voorkomen9.setPredicaat(Predicaat.K);
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(voorkomen2));
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(voorkomen3));
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(voorkomen4));
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(voorkomen5));
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(voorkomen6));
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(voorkomen7));
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(voorkomen8));
        assertFalse(voorkomen1.isInhoudelijkGelijkAan(voorkomen9));
    }

    private PersoonSamengesteldeNaamHistorie maakTemplateVoorkomen() {
        final String geslachtsnaamstam = "geslachtsnaamstam";
        final boolean indicatieAfgeleid = false;
        final boolean indicatieNamenreeks = false;
        final PersoonSamengesteldeNaamHistorie result = new PersoonSamengesteldeNaamHistorie(persoon, geslachtsnaamstam, indicatieAfgeleid,
            indicatieNamenreeks);
        result.setScheidingsteken(' ');
        result.setVoornamen("voornaam1 voornaam2");
        result.setVoorvoegsel("van");
        result.setAdellijkeTitel(AdellijkeTitel.P);
        result.setPredicaat(null);
        return result;
    }
}