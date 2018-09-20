/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import org.junit.Ignore;
import org.junit.Test;

public class Lo3HistorieConversieTest extends AbstractComponentTest {

    @Inject
    private Lo3HistorieConversie conversie;

    @Test
    public void testIstRelatieKind() {
        final BrpIstStandaardGroepInhoud.Builder standaardBuidler = new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_09, 0, 0);

        final TussenPersoonslijstBuilder migPlBuilder = new TussenPersoonslijstBuilder();
        final TussenRelatie.Builder relatieBuilder =
                new TussenRelatie.Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER);

        final BrpIstRelatieGroepInhoud.Builder kindIst = new BrpIstRelatieGroepInhoud.Builder(standaardBuidler.build());

        final BrpIstRelatieGroepInhoud kindIstInhoud = kindIst.build();
        final TussenStapel<BrpIstRelatieGroepInhoud> istStapel = maakMigratieStapel(kindIstInhoud);

        relatieBuilder.istKind(istStapel);
        migPlBuilder.relatie(relatieBuilder.build());
        migPlBuilder.istKindStapel(istStapel);

        final BrpPersoonslijst brpPL = conversie.converteer(migPlBuilder.build());

        assertNotNull(brpPL);
        final List<BrpRelatie> relaties = brpPL.getRelaties();
        assertNotNull(relaties);
        assertFalse(relaties.isEmpty());

        for (final BrpRelatie relatie : relaties) {
            if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(relatie.getSoortRelatieCode())
                && BrpSoortBetrokkenheidCode.OUDER.equals(relatie.getRolCode()))
            {
                final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel = relatie.getIstKindStapel();
                assertNotNull(istKindStapel);
                assertFalse(istKindStapel.isEmpty());
                assertEquals(kindIstInhoud, istKindStapel.get(0).getInhoud());
                break;
            }
        }

        final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels = brpPL.getIstKindStapels();
        assertNotNull(istKindStapels);
        assertFalse(istKindStapels.isEmpty());
        assertEquals(kindIstInhoud, istKindStapels.get(0).get(0).getInhoud());
    }

    @Test
    public void testIstRelatieKindMeerdereStapels() {
        final int aantalStapels = 2;
        final TussenPersoonslijstBuilder migPlBuilder = new TussenPersoonslijstBuilder();
        for (int stapelNr = 0; stapelNr < aantalStapels; stapelNr++) {
            final TussenRelatie.Builder relatieBuilder =
                    new TussenRelatie.Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER);
            final BrpIstStandaardGroepInhoud.Builder standaardBuidler = new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_09, stapelNr, 0);
            final BrpIstRelatieGroepInhoud.Builder kindIst = new BrpIstRelatieGroepInhoud.Builder(standaardBuidler.build());

            final BrpIstRelatieGroepInhoud kindIstInhoud = kindIst.build();
            final TussenStapel<BrpIstRelatieGroepInhoud> istStapel = maakMigratieStapel(kindIstInhoud);

            relatieBuilder.istKind(istStapel);

            migPlBuilder.relatie(relatieBuilder.build());
            migPlBuilder.istKindStapel(istStapel);
        }

        final BrpPersoonslijst brpPL = conversie.converteer(migPlBuilder.build());

        assertNotNull(brpPL);
        // 2 stapels zou als het goed is 2 relaties moeten opleveren met elk 1 IST-gegeven stapel
        final List<BrpRelatie> relaties = brpPL.getRelaties();
        assertNotNull(relaties);
        assertFalse(relaties.isEmpty());
        assertEquals("Aantal relaties klopt niet", aantalStapels, relaties.size());
        for (final BrpRelatie relatie : relaties) {
            if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(relatie.getSoortRelatieCode())
                && BrpSoortBetrokkenheidCode.OUDER.equals(relatie.getRolCode()))
            {
                final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel = relatie.getIstKindStapel();
                assertNotNull(istKindStapel);
                assertFalse(istKindStapel.isEmpty());
                assertEquals("Aantal ist stapels aan relatie klopt niet", 1, istKindStapel.size());
                break;
            }
        }

        // Er moeten dus 2 IST stapels (1 per kind) aan de persoon hangen
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels = brpPL.getIstKindStapels();
        assertNotNull(istKindStapels);
        assertFalse(istKindStapels.isEmpty());
        assertEquals("Aantal ist stapels aan persoon klopt niet", aantalStapels, istKindStapels.size());
    }

    @Test
    public void testIstRelatieKindMeerdereVoorkomens() {
        final TussenPersoonslijstBuilder migPlBuilder = new TussenPersoonslijstBuilder();

        final TussenRelatie.Builder relatieBuilder =
                new TussenRelatie.Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER);
        final TussenStapel<BrpIstRelatieGroepInhoud> istStapel = maakIstMeerdereVoorkomens();
        relatieBuilder.istKind(istStapel);
        migPlBuilder.relatie(relatieBuilder.build());
        migPlBuilder.istKindStapel(istStapel);

        final BrpPersoonslijst brpPL = conversie.converteer(migPlBuilder.build());

        assertNotNull(brpPL);
        // 2 voorkomens zou als het goed is 1 relatie moeten opleveren met 2 IST-gegeven stapels
        final List<BrpRelatie> relaties = brpPL.getRelaties();
        assertNotNull(relaties);
        assertFalse(relaties.isEmpty());
        assertEquals("Aantal relaties klopt niet", 1, relaties.size());
        for (final BrpRelatie relatie : relaties) {
            if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(relatie.getSoortRelatieCode())
                && BrpSoortBetrokkenheidCode.OUDER.equals(relatie.getRolCode()))
            {
                final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel = relatie.getIstKindStapel();
                assertNotNull(istKindStapel);
                assertFalse(istKindStapel.isEmpty());
                assertEquals("Aantal ist stapels aan relatie klopt niet", 2, istKindStapel.size());
                break;
            }
        }

        // Er moeten dus 1 IST stapel met 2 groepen aan de persoon hangen
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels = brpPL.getIstKindStapels();
        assertNotNull(istKindStapels);
        assertFalse(istKindStapels.isEmpty());
        assertEquals("Aantal ist stapels aan persoon klopt niet", 1, istKindStapels.size());
        assertEquals("Aantal ist groepen bij persoon klopt niet", 2, istKindStapels.get(0).size());
    }

    private TussenStapel<BrpIstRelatieGroepInhoud> maakIstMeerdereVoorkomens() {
        final List<BrpIstRelatieGroepInhoud> istGegevens = new ArrayList<>();
        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_09, 0, 0);
        final BrpIstRelatieGroepInhoud.Builder kindIst = new BrpIstRelatieGroepInhoud.Builder(standaardBuilder.build());

        final BrpIstStandaardGroepInhoud.Builder standaardBuilder2 = new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_59, 0, 1);
        final BrpIstRelatieGroepInhoud.Builder kindIst2 = new BrpIstRelatieGroepInhoud.Builder(standaardBuilder2.build());

        istGegevens.add(kindIst2.build());
        istGegevens.add(kindIst.build());
        return maakMigratieStapel(istGegevens);
    }

    @Ignore("Verder afmaken en dan Logging enablen")
    @Test
    public void testIstRelatieLegeRelatieGevuldeIst() {
        final TussenPersoonslijstBuilder migPlBuilder = new TussenPersoonslijstBuilder();

        final TussenRelatie.Builder relatieBuilder =
                new TussenRelatie.Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER);

        // IST gegevens
        final TussenStapel<BrpIstRelatieGroepInhoud> istGegevens = maakIstMeerdereVoorkomens();

        // Betrokkene / lege LO3 rij
        final BrpIdentificatienummersInhoud idInhoud = new BrpIdentificatienummersInhoud(null, null);
        final BrpGeboorteInhoud geboorteInhoud = new BrpGeboorteInhoud(null, null, null, null, null, null, null);
        final BrpSamengesteldeNaamInhoud naamInhoud =
                new BrpSamengesteldeNaamInhoud(null, null, null, null, null, null, new BrpBoolean(false, null), new BrpBoolean(false, null));
        final BrpOuderInhoud ouderInhoud = new BrpOuderInhoud(null, null);

        final TussenBetrokkenheid betrokkenheidStapelKind =
                new TussenBetrokkenheid(
                    BrpSoortBetrokkenheidCode.KIND,
                    maakStapel(idInhoud),
                    null,
                    maakStapel(geboorteInhoud),
                    null,
                    maakStapel(naamInhoud),
                    maakStapel(ouderInhoud));

        relatieBuilder.betrokkenheden(Arrays.asList(betrokkenheidStapelKind));
        relatieBuilder.istKind(istGegevens);
        migPlBuilder.relatie(relatieBuilder.build());

        final BrpPersoonslijst brpPL = conversie.converteer(migPlBuilder.build());

        assertNotNull(brpPL);
        // 2 voorkomens zou als het goed is 1 relatie moeten opleveren met 2 IST-gegeven stapels
        final List<BrpRelatie> relaties = brpPL.getRelaties();
        assertNotNull(relaties);
        assertFalse(relaties.isEmpty());
        assertEquals("Aantal relaties klopt niet", 1, relaties.size());
        for (final BrpRelatie relatie : relaties) {
            if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(relatie.getSoortRelatieCode())
                && BrpSoortBetrokkenheidCode.OUDER.equals(relatie.getRolCode()))
            {
                final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel = relatie.getIstKindStapel();
                assertNotNull(istKindStapel);
                assertFalse(istKindStapel.isEmpty());
                assertEquals("Aantal ist groepen bij relatie klopt niet", 2, istKindStapel.size());
                break;
            }
        }

        // Er moeten dus 1 IST stapel met 2 groepen aan de persoon hangen
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels = brpPL.getIstKindStapels();
        assertNotNull(istKindStapels);
        assertFalse(istKindStapels.isEmpty());
        assertEquals("Aantal ist stapels aan persoon klopt niet", 1, istKindStapels.size());
        assertEquals("Aantal ist groepen bij persoon klopt niet", 2, istKindStapels.get(0).size());
    }

    private <T extends BrpGroepInhoud> TussenStapel<T> maakStapel(final T inhoud) {
        return new TussenStapel<>(Arrays.asList(new TussenGroep<>(inhoud, Lo3Historie.NULL_HISTORIE, null, null)));
    }

    private <T extends AbstractBrpIstGroepInhoud> TussenStapel<T> maakMigratieStapel(final T inhoud) {
        return maakMigratieStapel(Arrays.asList(inhoud));
    }

    private <T extends AbstractBrpIstGroepInhoud> TussenStapel<T> maakMigratieStapel(final List<T> inhouden) {
        final List<TussenGroep<T>> groepen = new ArrayList<>();
        for (final T inhoud : inhouden) {
            groepen.add(new TussenGroep<>(inhoud, Lo3Historie.NULL_HISTORIE, null, null));
        }
        return new TussenStapel<>(groepen);
    }
}
