/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;

import org.junit.Test;

public class Casus23IndicatieTest extends AbstractCasusTest {

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

    private final Lo3NationaliteitInhoud lo3BijzonderNl = new Lo3NationaliteitInhoud(
            null,
            null,
            null,
            Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.asElement(), null);
    private final Lo3NationaliteitInhoud lo3VastgesteldNietNl = new Lo3NationaliteitInhoud(
            null,
            null,
            null,
            Lo3AanduidingBijzonderNederlandschapEnum.VASTGESTELD_NIET_NEDERLANDER.asElement(), null);
    private final BrpBehandeldAlsNederlanderIndicatieInhoud brpBehandeldAlsNl =
            new BrpBehandeldAlsNederlanderIndicatieInhoud(new BrpBoolean(true), null, null);
    private final BrpVastgesteldNietNederlanderIndicatieInhoud brpVastgesteldNietNl = new BrpVastgesteldNietNederlanderIndicatieInhoud(new BrpBoolean(
            true), null, null);
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brpBijzNl1 = new BrpTestObject<>();
    private final BrpTestObject<BrpVastgesteldNietNederlanderIndicatieInhoud> brpVastgesteldNietNl1 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19900109, null), "2A");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102, null), "1A");

        // @formatter:off
        // V 8-1-1990 9-1-1990 <A2>
        // B O 1-1-1990 2-1-1990 <A1>

        // LO3 input
        lo2.vul(lo3VastgesteldNietNl, null, 19900108, 19900109, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo1.vul(lo3BijzonderNl, ONJUIST, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));

        // verwachte BRP output
        // bijz:
        // Ja 1-1-1990 null 2-1-1990 2-1-1990 A1 x A1
        brpBijzNl1.vul(brpBehandeldAlsNl, 19900101, null, 19900102010000L, 19900102010000L, 'O', actie1, null, actie1);
        // vastgesteld niet NL
        // Ja 8-1-1990 xxxxxxxx 9-1-1990 xxxxxxxx A2 x x
        brpVastgesteldNietNl1.vul(brpVastgesteldNietNl, 19900108, null, 19900109010000L, null, null, actie2, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2);

    }

    @Override
    protected Lo3Stapel<Lo3NationaliteitInhoud> maakNationaliteitStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3NationaliteitInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3NationaliteitInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> indicatieBijzNlStapel = brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel();
        assertEquals(1, indicatieBijzNlStapel.size());

        assertBehandeldAlsNederlander(indicatieBijzNlStapel.get(0), brpBijzNl1);

        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> indicatieNietNlStapel =
                brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel();
        assertEquals(1, indicatieNietNlStapel.size());

        assertVastgesteldNietNederlander(indicatieNietNlStapel.get(0), brpVastgesteldNietNl1);
    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> rondverteerdeStapels = terug.getNationaliteitStapels();
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> origineleStapels = getLo3Persoonslijst().getNationaliteitStapels();
        assertEquals(1, rondverteerdeStapels.size());
        assertEquals(origineleStapels.get(0).size(), rondverteerdeStapels.get(0).size());
        assertEquals(origineleStapels, rondverteerdeStapels);
    }
}
