/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoResultaat;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoResultaatIndex;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.gba.gbav.spontaan.verschilanalyse.VoorkomenMatch;
import org.junit.Test;

public class GgoPersoonslijstGroepBuilderTest {
    private final GgoPersoonslijstGroepBuilder ggoPlGroepBuilder = new GgoPersoonslijstGroepBuilder();

    @Test
    public void bepaalResultaatIndexInlezenGbaPlNietGeslaagd() {
        final Lo3Persoonslijst lo3Persoonslijst = null;
        final BrpPersoonslijst brpPersoonslijst = null;
        final List<GgoFoutRegel> foutRegels = createFoutRegels(1);
        final List<GgoVoorkomenVergelijking> matches = createVerschillen(0);

        final List<GgoResultaatIndex> resultaatIndex =
                ggoPlGroepBuilder.bepaalResultaatIndex(lo3Persoonslijst, brpPersoonslijst, foutRegels, matches);

        assertEquals("Moet 1 resultaat in zitten", 1, resultaatIndex.size());
        assertEquals("GgoResultaat niet gelijk", GgoResultaat.INLEZEN_GBA_PL_NIET_GESLAAGD, resultaatIndex.get(0).getResultaat());
    }

    @Test
    public void bepaalResultaatIndexConversieNietGeslaagd() {
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstBuilder().build();
        final BrpPersoonslijst brpPersoonslijst = null;
        final List<GgoFoutRegel> foutRegels = createFoutRegels(1);
        final List<GgoVoorkomenVergelijking> matches = createVerschillen(0);

        final List<GgoResultaatIndex> resultaatIndex =
                ggoPlGroepBuilder.bepaalResultaatIndex(lo3Persoonslijst, brpPersoonslijst, foutRegels, matches);

        assertEquals("Moet 1 resultaat in zitten", 1, resultaatIndex.size());
        assertEquals("GgoResultaat niet gelijk", GgoResultaat.CONVERSIE_NIET_GESLAAGD, resultaatIndex.get(0).getResultaat());
    }

    @Test
    public void bepaalResultaatIndexConversieGeslaagdMetMeldingen() {
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstBuilder().build();
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final List<GgoFoutRegel> foutRegels = createFoutRegels(2);
        final List<GgoVoorkomenVergelijking> matches = createVerschillen(0);

        final List<GgoResultaatIndex> resultaatIndex =
                ggoPlGroepBuilder.bepaalResultaatIndex(lo3Persoonslijst, brpPersoonslijst, foutRegels, matches);

        assertTrue("Moeten 2 resultaten in zitten", resultaatIndex.size() == 2);
        assertTrue("Moet meldingen bevatten", resultaatIndex.get(0).getAantalMeldingen() > 0);
        assertEquals("GgoResultaat niet gelijk", GgoResultaat.CONVERSIE_GESLAAGD, resultaatIndex.get(0).getResultaat());
    }

    @Test
    public void bepaalResultaatIndexConversieZonderMeldingen() {
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstBuilder().build();
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final List<GgoFoutRegel> foutRegels = createFoutRegels(0);
        final List<GgoVoorkomenVergelijking> matches = createVerschillen(0);

        final List<GgoResultaatIndex> resultaatIndex =
                ggoPlGroepBuilder.bepaalResultaatIndex(lo3Persoonslijst, brpPersoonslijst, foutRegels, matches);

        assertTrue("Moeten 2 resultaten in zitten", resultaatIndex.size() == 2);
        assertTrue("Mag geen meldingen bevatten", resultaatIndex.get(0).getAantalMeldingen() == 0);
        assertEquals("GgoResultaat niet gelijk", GgoResultaat.CONVERSIE_GESLAAGD, resultaatIndex.get(0).getResultaat());
    }

    @Test
    public void bepaalResultaatIndexTerugconversieNietGeslaagd() {
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstBuilder().build();
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final List<GgoFoutRegel> foutRegels = createFoutRegels(0);
        final List<GgoVoorkomenVergelijking> matches = null;

        final List<GgoResultaatIndex> resultaatIndex =
                ggoPlGroepBuilder.bepaalResultaatIndex(lo3Persoonslijst, brpPersoonslijst, foutRegels, matches);

        assertTrue("Moeten 2 resultaten in zitten", resultaatIndex.size() == 2);
        assertTrue("Mag geen verschillen bevatten", resultaatIndex.get(1).getAantalMeldingen() == 0);
        assertEquals("GgoResultaat niet gelijk", GgoResultaat.TERUGCONVERSIE_NIET_GESLAAGD, resultaatIndex.get(1).getResultaat());
    }

    @Test
    public void bepaalResultaatIndexTerugconversieMetVerschillen() {
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstBuilder().build();
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final List<GgoFoutRegel> foutRegels = createFoutRegels(0);
        final List<GgoVoorkomenVergelijking> matches = createVerschillen(2);

        final List<GgoResultaatIndex> resultaatIndex =
                ggoPlGroepBuilder.bepaalResultaatIndex(lo3Persoonslijst, brpPersoonslijst, foutRegels, matches);

        assertTrue("Moeten 2 resultaten in zitten", resultaatIndex.size() == 2);
        assertTrue("Moet verschillen bevatten", resultaatIndex.get(1).getAantalMeldingen() > 0);
        assertEquals("GgoResultaat niet gelijk", GgoResultaat.TERUGCONVERSIE_GESLAAGD, resultaatIndex.get(1).getResultaat());
    }

    @Test
    public void bepaalResultaatIndexTerugconversieZonderVerschillen() {
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstBuilder().build();
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final List<GgoFoutRegel> foutRegels = createFoutRegels(0);
        final List<GgoVoorkomenVergelijking> matches = createVerschillen(0);

        final List<GgoResultaatIndex> resultaatIndex =
                ggoPlGroepBuilder.bepaalResultaatIndex(lo3Persoonslijst, brpPersoonslijst, foutRegels, matches);

        assertTrue("Moeten 2 resultaten in zitten", resultaatIndex.size() == 2);
        assertTrue("Mag geen verschillen bevatten", resultaatIndex.get(1).getAantalMeldingen() == 0);
        assertEquals("GgoResultaat niet gelijk", GgoResultaat.TERUGCONVERSIE_GESLAAGD, resultaatIndex.get(1).getResultaat());
    }

    private List<GgoFoutRegel> createFoutRegels(final int aantal) {
        final List<GgoFoutRegel> foutRegels = new ArrayList<>();
        for (int i = 0; i < aantal; i++) {
            foutRegels.add(new GgoFoutRegel(null, LogSeverity.ERROR, null, "code" + i, "omschrijving" + i));
        }
        return foutRegels;
    }

    private List<GgoVoorkomenVergelijking> createVerschillen(final int aantal) {
        final List<GgoVoorkomenVergelijking> matches = new ArrayList<>();
        for (int i = 0; i < aantal; i++) {
            matches.add(new GgoVoorkomenVergelijking(new VoorkomenMatch(1), (long) i, "changed"));
        }
        return matches;
    }
}
