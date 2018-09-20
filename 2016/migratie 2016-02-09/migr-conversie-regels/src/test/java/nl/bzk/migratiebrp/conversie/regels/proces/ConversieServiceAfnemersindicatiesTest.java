/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

/**
 * Test converteren van afnemersindicaties.
 */
public class ConversieServiceAfnemersindicatiesTest extends AbstractConversieServiceAfnemersindicatiesTest {

    @Test
    public void testOudVoorbeeld1() {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersIndicatieStapels = new ArrayList<>();

        // @formatter:off
        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(7, 19920101, 0, 0)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(null, 19960101, 1, 0), maakLo3Categorie(8, 19920601, 1, 1)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(9, 19920501, 2, 0)));
        // @formatter:on

        final BrpAfnemersindicaties resultaat = test("OUD-VOORBEELD-1", new Lo3Afnemersindicatie(9734838049L, afnemersIndicatieStapels));
        Assert.assertEquals(3, resultaat.getAfnemersindicaties().size());
    }

    @Test
    public void testOudVoorbeeld2() {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersIndicatieStapels = new ArrayList<>();

        // @formatter:off
        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(7, 20010101, 0, 0)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(null, 19950101, 1, 0), maakLo3Categorie(7, 19940101, 1, 1)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(9, 19920501, 2, 0)));
        // @formatter:on

        final BrpAfnemersindicaties resultaat = test("OUD-VOORBEELD-2", new Lo3Afnemersindicatie(7396715809L, afnemersIndicatieStapels));
        Assert.assertEquals(2, resultaat.getAfnemersindicaties().size());
    }

    @Test
    public void testOudVoorbeeld3() {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersIndicatieStapels = new ArrayList<>();

        // @formatter:off
        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(7, 20010101, 0, 0),
                                                               maakLo3Categorie(7, 19960101, 0, 1),
                                                               maakLo3Categorie(null, 19940101, 0, 2),
                                                               maakLo3Categorie(7, 19930101, 0, 3)));
        // @formatter:on

        final BrpAfnemersindicaties resultaat = test("OUD-VOORBEELD-3", new Lo3Afnemersindicatie(4862398753L, afnemersIndicatieStapels));
        Assert.assertEquals(1, resultaat.getAfnemersindicaties().size());
    }

    @Test
    public void testVoorbeeld1() {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersIndicatieStapels = new ArrayList<>();

        // @formatter:off
        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(7, 19920101, 0, 0)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(null, 19960101, 1, 0),
                                                               maakLo3Categorie(8, 19930601, 1, 1),
                                                               maakLo3Categorie(8, 19920501, 1, 2)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(9, 19920501, 2, 0)));
        // @formatter:on

        final BrpAfnemersindicaties resultaat = test("VOORBEELD-1", new Lo3Afnemersindicatie(9734838049L, afnemersIndicatieStapels));
        Assert.assertEquals(3, resultaat.getAfnemersindicaties().size());
    }

    @Test
    public void testVoorbeeld2() {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersIndicatieStapels = new ArrayList<>();

        // @formatter:off
        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(7, 19970601, 0, 0), maakLo3Categorie(7, 19960101, 0, 1)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(null, 19950101, 1, 0), maakLo3Categorie(7, 19940101, 1, 1)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(9, 19920501, 2, 0)));
        // @formatter:on

        final BrpAfnemersindicaties resultaat = test("VOORBEELD-2", new Lo3Afnemersindicatie(7396715809L, afnemersIndicatieStapels));
        Assert.assertEquals(2, resultaat.getAfnemersindicaties().size());
    }

    @Test
    public void testVoorbeeld3() {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersIndicatieStapels = new ArrayList<>();

        // @formatter:off
        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(7, 20010101, 0, 0),
                                                               maakLo3Categorie(7, 19960101, 0, 1),
                                                               maakLo3Categorie(7, 19930101, 0, 2)));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(maakLo3Categorie(7, 19950101, 1, 0), maakLo3Categorie(7, 19920101, 1, 1)));

        // @formatter:on

        final BrpAfnemersindicaties resultaat = test("VOORBEELD-3", new Lo3Afnemersindicatie(4862398753L, afnemersIndicatieStapels));
        Assert.assertEquals(1, resultaat.getAfnemersindicaties().size());
    }

}
