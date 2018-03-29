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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;

import org.junit.Test;

public class Casus19IndicatieTest extends AbstractCasusTest {

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

    private final Lo3NationaliteitInhoud lo3Leeg = new Lo3NationaliteitInhoud(null, null, null, null, null);
    private final Lo3NationaliteitInhoud lo3BijzonderNl = new Lo3NationaliteitInhoud(
            null,
            null,
            null,
            Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.asElement()
            , null);
    private final BrpBehandeldAlsNederlanderIndicatieInhoud brpBehandeldAlsNl =
            new BrpBehandeldAlsNederlanderIndicatieInhoud(new BrpBoolean(true), null, null);
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp1 = new BrpTestObject<>();
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp2 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19960102, null), "3A");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19950102, null), "2A", BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102, null), "1A");

        // @formatter:off
        // <leeg> 1-1-1996 2-1-1996 <A3>
        // <leeg> O 1-1-1995 2-1-1995 <A2>
        // B 1-1-1990 2-1-1990 <A1>

        // LO3 input
        lo3.vul(lo3Leeg, null, 19960101, 19960102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo2.vul(lo3Leeg, ONJUIST, 19950101, 19950102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));
        lo1.vul(lo3BijzonderNl, null, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 2));

        // Ja 1-1-1990 1-1-1995 2-1-1990 xx-xx-xxxx A1 A2 A1
        // Ja 1-1-1990 1-1-1996 2-1-1990 xx-xx-xxxx A1 A3 X

        // verwachte BRP output
        brp1.vul(brpBehandeldAlsNl, 19900101, 19960101, 19900102010000L, null, null, actie1, actie3, null);
        brp2.vul(brpBehandeldAlsNl, 19900101, 19950101, 19900102010100L, 19900102010100L, 'O', actie1, actie2, actie1);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3);

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
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> indicatieStapel = brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel();
        assertEquals(2, indicatieStapel.size());

        assertBehandeldAlsNederlander(indicatieStapel.get(0), brp1);
        assertBehandeldAlsNederlander(indicatieStapel.get(1), brp2);
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
