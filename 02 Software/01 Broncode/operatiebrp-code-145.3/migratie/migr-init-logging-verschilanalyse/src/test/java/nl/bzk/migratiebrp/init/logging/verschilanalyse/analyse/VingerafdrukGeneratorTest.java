/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;
import nl.bzk.migratiebrp.init.logging.model.VerschilType;
import nl.bzk.migratiebrp.init.logging.model.VoorkomenMatch;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.VerschilAnalyseRegel;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl.TestUtil;
import org.junit.Test;

public class VingerafdrukGeneratorTest {

    @Test
    public void testEnkeleRegel() {
        final List<VerschilAnalyseRegel> regels = new ArrayList<>();
        final VerschilAnalyseRegel regel = new VerschilAnalyseRegel(TestUtil.getHerkomst(), VerschilType.ADDED, "8510");
        regels.add(regel);
        final String afdruk = VingerafdrukGenerator.maakVingerafdruk(regels);
        assertNotNull(afdruk);
        assertEquals("C01S00V008510A", afdruk);
    }

    @Test
    public void testGeenRegels() {
        assertNull(VingerafdrukGenerator.maakVingerafdruk(Collections.<VerschilAnalyseRegel>emptyList()));
    }

    @Test
    public void testCategorieVerwijderd() {
        final List<VerschilAnalyseRegel> regels = new ArrayList<>();
        final StapelMatch<Lo3PersoonInhoud> stapelMatch = new StapelMatch<>(TestUtil.getHerkomst());
        stapelMatch.addLo3Stapel(TestUtil.maakStapel());
        final VerschilAnalyseRegel regel = new VerschilAnalyseRegel(stapelMatch);
        regels.add(regel);
        final String afdruk = VingerafdrukGenerator.maakVingerafdruk(regels);
        assertNotNull(afdruk);
        assertEquals("C01S00R", afdruk);
    }

    @Test
    public void testVoorkomenVerwijderd() {
        final List<VerschilAnalyseRegel> regels = new ArrayList<>();
        final Lo3Categorie<Lo3PersoonInhoud> voorkomen = TestUtil.maakVoorkomen(false);

        final VoorkomenMatch<Lo3PersoonInhoud> voorkomenMatch = new VoorkomenMatch<>(TestUtil.getHerkomst());
        voorkomenMatch.addLo3Voorkomen(voorkomen);
        voorkomenMatch.addBrpLo3Voorkomen(voorkomen);
        voorkomenMatch.addBrpLo3Voorkomen(TestUtil.maakVoorkomen(true));

        final VerschilAnalyseRegel regel = new VerschilAnalyseRegel(voorkomenMatch);
        regels.add(regel);
        final String afdruk = VingerafdrukGenerator.maakVingerafdruk(regels);
        assertNotNull(afdruk);
        assertEquals("C01S00V00NON_UNIQUE_MATCHED", afdruk);
    }
}
