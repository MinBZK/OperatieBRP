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
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;

import org.junit.Test;

public class Casus21IndicatieTest extends AbstractCasusTest {

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

    private final Lo3NationaliteitInhoud lo3Leeg = new Lo3NationaliteitInhoud(null, null, null, null);
    private final Lo3NationaliteitInhoud lo3BijzonderNl = new Lo3NationaliteitInhoud(
        null,
        null,
        null,
        Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.asElement());
    private final BrpBehandeldAlsNederlanderIndicatieInhoud brpBehandeldAlsNl =
            new BrpBehandeldAlsNederlanderIndicatieInhoud(new BrpBoolean(true), null, null);
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp1 = new BrpTestObject<>();
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp2 = new BrpTestObject<>();
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp3 = new BrpTestObject<>();
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp4 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19950103, null), "4A");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950102, null), "3A", BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19900103, null), "2A");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102, null), "1A");

        // @formatter:off
        // B 1-1-1995 3-1-1995 <A4>
        // <leeg> O 1-1-1995 2-1-1995 <A3>
        // B 1-1-1989 3-1-1990 <A2>
        // B O 1-1-1990 2-1-1990 <A1>

        // LO3 input
        lo4.vul(lo3BijzonderNl, null, 19950101, 19950103, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo3.vul(lo3Leeg, ONJUIST, 19950101, 19950102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));
        lo2.vul(lo3BijzonderNl, null, 19890101, 19900103, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 2));
        lo1.vul(lo3BijzonderNl, ONJUIST, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 3));

        // Ja 1-1-1995 xxxxxxxx 3-1-1995 xxxxxxxx A4 x x
        // Ja 1-1-1989 1-1-1995 3-1-1990 xxxxxxxx A2 x x
        // Ja 1-1-1989 1-1-1995 3-1-1990 3-1-1990 A2 A3 A2
        // Ja 1-1-1990 xxxxxxxx 2-1-1990 2-1-1990 A1 x A1

        // verwachte BRP output
        brp1.vul(brpBehandeldAlsNl, 19900101, null, 19900102010000L, 19900102010000L, 'O', actie1, null, actie1);
        brp2.vul(brpBehandeldAlsNl, 19890101, 19950101, 19900103010000L, null, null, actie2, actie4, null);
        brp3.vul(brpBehandeldAlsNl, 19890101, 19950101, 19900103010100L, 19900103010100L, 'O', actie2, actie3, actie2);
        brp4.vul(brpBehandeldAlsNl, 19950101, null, 19950103010000L, null, null, actie4, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4);

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
        assertEquals(4, indicatieStapel.size());

        assertBehandeldAlsNederlander(indicatieStapel.get(0), brp1);
        assertBehandeldAlsNederlander(indicatieStapel.get(1), brp2);
        assertBehandeldAlsNederlander(indicatieStapel.get(2), brp3);
        assertBehandeldAlsNederlander(indicatieStapel.get(3), brp4);
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
        Lo3StapelHelper.vergelijk(origineleStapels, rondverteerdeStapels);
    }
}
